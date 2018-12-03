package ru.galuzin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Account {
    @Setter
    private Long id;
    final private String email;
    final private String name;
    final private byte[] password;

    public Account(String email, String name, byte[] password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
