package ru.galuzin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Account {
    final private String uid;
    final private String email;
    final private String name;
    final private byte[] password;
}
