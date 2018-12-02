package ru.galuzin.util;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValueHolder <T>{
    T value;
}
