package com.example.sungshinsos;

public class EmergencyContact {
    private String name;
    private String phoneNumber;

    public EmergencyContact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + "," + phoneNumber;
    }

    public static EmergencyContact fromString(String contactString) {
        String[] parts = contactString.split(",");
        return new EmergencyContact(parts[0], parts[1]);
    }
}
