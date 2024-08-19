package com.example.sungshinsos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmergencyContactsAdapter extends RecyclerView.Adapter<EmergencyContactsAdapter.ViewHolder> {

    private List<EmergencyContact> contactsList;
    private Context context;

    public EmergencyContactsAdapter(Context context, List<EmergencyContact> contactsList) {
        this.contactsList = contactsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emergency_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EmergencyContact contact = contactsList.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewPhoneNumber.setText(contact.getPhoneNumber());

        // 기본 연락처는 수정/삭제 버튼을 숨김
        if (MapActivity.defaultContacts.contains(contact)) {
            holder.buttonEdit.setVisibility(View.GONE);
            holder.buttonDelete.setVisibility(View.GONE);
        } else {
            holder.buttonEdit.setVisibility(View.VISIBLE);
            holder.buttonDelete.setVisibility(View.VISIBLE);
        }

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(contact, position);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    private void showEditDialog(EmergencyContact contact, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("연락처 수정");

        // dialog_edit_contact.xml을 인플레이션
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_contact, (ViewGroup) null, false);
        final EditText inputName = viewInflated.findViewById(R.id.editTextEditName); // 올바른 ID
        final EditText inputPhoneNumber = viewInflated.findViewById(R.id.editTextEditPhoneNumber); // 올바른 ID

        inputName.setText(contact.getName());
        inputPhoneNumber.setText(contact.getPhoneNumber());

        builder.setView(viewInflated);

        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                contact.setName(inputName.getText().toString());
                contact.setPhoneNumber(inputPhoneNumber.getText().toString());
                notifyItemChanged(position);
                saveContacts();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteContact(int position) {
        contactsList.remove(position);
        notifyItemRemoved(position);
        saveContacts();
    }

    private void saveContacts() {
        SharedPreferences preferences = context.getSharedPreferences(MapActivity.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder contactsStringBuilder = new StringBuilder();
        for (EmergencyContact contact : contactsList) {
            if (!MapActivity.defaultContacts.contains(contact)) {
                contactsStringBuilder.append(contact.toString()).append(";");
            }
        }
        editor.putString(MapActivity.CONTACTS_KEY, contactsStringBuilder.toString());
        editor.apply();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPhoneNumber;
        ImageButton buttonEdit, buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public void loadContacts() {
        SharedPreferences preferences = context.getSharedPreferences(MapActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String contactsString = preferences.getString(MapActivity.CONTACTS_KEY, "");
        String[] contactsArray = contactsString.split(";");
        contactsList.clear();
        for (String contactString : contactsArray) {
            if (!contactString.trim().isEmpty()) {
                EmergencyContact contact = EmergencyContact.fromString(contactString);
                contactsList.add(contact);
            }
        }
        notifyDataSetChanged(); // RecyclerView 갱신
    }
}
