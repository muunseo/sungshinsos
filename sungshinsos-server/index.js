const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const admin = require('firebase-admin');
const app = express();
const port = process.env.PORT || 3000; // 포트 번호를 환경 변수로 관리

admin.initializeApp({
    credential: admin.credential.cert(require('./serviceAccountKey.json')),
    databaseURL: process.env.FIREBASE_DATABASE_URL || "https://sungshinsos-51c75-default-rtdb.firebaseio.com"
});

// CORS 설정
app.use(cors({
    origin: '*',
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// 유니크 ID 생성 함수 (예시)
function generateUniqueId(prefix) {
    return `${prefix}_${Date.now()}`;
}

// FCM 메시지 전송 함수
async function sendNotification(title, body, token, ringtone) {
    const message = {
        notification: {
            title: title,
            body: body,
        },
        token: token,
        android: {
            notification: {
                sound: ringtone || "default",
            },
        },
        apns: {
            payload: {
                aps: {
                    sound: ringtone || "default",
                },
            },
        },
    };

    try {
        await admin.messaging().send(message);
        console.log(`Successfully sent notification: ${title}`);
    } catch (error) {
        console.error(`Error sending notification: ${title}`, error);
    }
}

// 게시물 작성 이벤트를 처리하는 엔드포인트
app.post('/post-created', async (req, res) => {
    console.log('Post created request body:', req.body);

    const { title, content, userId, timestamp } = req.body;

    if (!title || !content || !userId) {
        return res.status(400).json({ error: 'Title, content, and User ID are required.' });
    }

    const newPostId = generateUniqueId('post'); // 새로운 게시물 ID 생성
    const newPost = {
        postId: newPostId,
        title: title,
        content: content,
        userId: userId,
        timestamp: timestamp
    };

    try {
        await admin.database().ref('Post').child(newPostId).set(newPost);

        const usersSnapshot = await admin.database().ref('users').once('value');
        const users = usersSnapshot.val();

        const messages = [];
        for (let uid in users) {
            const user = users[uid];

            if (user.notificationSettings &&
                user.notificationSettings.notificationsEnabled &&
                user.notificationSettings.postCreateEnabled &&
                //uid !== userId && // 게시물 작성자는 제외
                user.fcmToken) {

                messages.push({
                    title: `New post has been created.`,
                    body: title,
                    token: user.fcmToken,
                    ringtone: user.notificationSettings.postCreateRingtone
                });
            }
        }

        if (messages.length === 0) {
            return res.json({ message: 'No notifications to send.' });
        }

        for (const msg of messages) {
            await sendNotification(msg.title, msg.body, msg.token, msg.ringtone);
        }

        res.json({ postId: newPostId, message: 'Post creation notifications sent successfully.' });
    } catch (error) {
        console.error('Error processing request:', error);
        res.status(500).json({ error: 'Error processing request.' });
    }
});

// 게시물 수정 이벤트를 처리하는 엔드포인트
app.post('/post-updated', async (req, res) => {
    console.log('Post updated request body:', req.body);

    const { postId, title, content, userId } = req.body;

    if (!postId || !title || !content) {
        return res.status(400).json({ error: 'Post ID, title, and content are required.' });
    }

    const postIdPattern = /^post_\d+$/;
    if (!postIdPattern.test(postId)) {
        return res.status(400).json({ error: 'Invalid Post ID format.' });
    }

    try {
        const postRef = admin.database().ref('Post').child(postId);
        const postSnapshot = await postRef.once('value');
        const post = postSnapshot.val();

        if (!post) {
            return res.status(404).json({ error: 'Post not found.' });
        }

        await postRef.update({ title, content });

        const usersSnapshot = await admin.database().ref('users').once('value');
        const users = usersSnapshot.val();

        const messages = [];
        for (let uid in users) {
            const user = users[uid];

            if (user.notificationSettings &&
                user.notificationSettings.notificationsEnabled &&
                user.notificationSettings.postUpdateEnabled &&
                //uid !== post.userId && // 게시물 작성자는 제외
                user.fcmToken) {

                messages.push({
                    title: `The post: ${title} has been updated.`,
                    body: content,
                    token: user.fcmToken,
                    ringtone: user.notificationSettings.postUpdateRingtone
                });
            }
        }

        if (messages.length === 0) {
            return res.json({ message: 'No notifications to send.' });
        }

        for (const msg of messages) {
            await sendNotification(msg.title, msg.body, msg.token, msg.ringtone);
        }

        res.json({ postId: postId, message: 'Post update notifications sent successfully.' });
    } catch (error) {
        console.error('Error processing request:', error);
        res.status(500).json({ error: 'Error processing request.' });
    }
});

app.post('/comment-created', async (req, res) => {
    console.log('Comment created request body:', req.body);

    const { postId, commentText, userId, username } = req.body;

    if (!postId || !commentText || !userId || !username) {
        return res.status(400).json({ error: 'Post ID, comment text, User ID, and username are required.' });
    }

    const newCommentId = generateUniqueId('comment'); // 새로운 댓글 ID 생성
    const newComment = {
        commentId: newCommentId,
        postId: postId,
        commentText: commentText,
        userId: userId,
        username: username,
        timestamp: Date.now()
    };

    try {
        // 댓글을 Firebase Realtime Database에 저장
        await admin.database().ref('Post').child(postId).child('comments').child(newCommentId).set(newComment);

        // 포스트 정보를 가져와서 포스트 작성자의 FCM 토큰을 조회
        const postSnapshot = await admin.database().ref('Post').child(postId).once('value');
        const post = postSnapshot.val();

        // 댓글 작성자의 ID를 제외한 사용자들 중에서 알림을 보내야 할 사용자들을 필터링
        const usersSnapshot = await admin.database().ref('users').once('value');
        const users = usersSnapshot.val();

        const messages = [];
        for (let uid in users) {
            const user = users[uid];

            // 알림 설정이 활성화되어 있고, 댓글 작성 알림을 허용한 사용자만 필터링
            if (user.notificationSettings &&
                user.notificationSettings.notificationsEnabled &&
                user.notificationSettings.commentCreateEnabled &&
                //uid !== post.userId && // 게시물 작성자는 제외
                user.fcmToken) {

                messages.push({
                    title: `${username} has posted a comment.`,
                    body: commentText,
                    token: user.fcmToken,
                    ringtone: user.notificationSettings.commentCreateRingtone
                });
            }
        }

        // 필터링된 사용자들에게 알림을 전송
        if (messages.length === 0) {
            return res.json({ message: 'No notifications to send.' });
        }

        for (const msg of messages) {
            await sendNotification(msg.title, msg.body, msg.token, msg.ringtone);
        }

        res.json({ commentId: newCommentId, message: 'Comment created and notifications sent successfully.' });
    } catch (error) {
        console.error('Error processing request:', error);
        res.status(500).json({ error: 'Error processing request.' });
    }
});

// 서버 시작
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
