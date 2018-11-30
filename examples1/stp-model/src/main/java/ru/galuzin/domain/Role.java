package ru.galuzin.model;

import java.util.Arrays;

public enum Role {
    USER(2),
    ADMIN(1);

    private final int code;

    Role(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public static Role getByCode(int code){
        return Arrays.stream(Role.values()).filter(role->role.getCode()==code)
                .findAny().orElse(null);
    }

}
