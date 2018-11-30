package ru.galuzin.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValueHolder <T>{
    T value;
}
