package ru.galuzin.model;

public class Account {
    final private String uid;
    final private String email;
    final private String name;
    final private byte[] password;

    public Account(String uid, String email, String name, byte[] password) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public byte[] getPassword() {
        return password;
    }
}
