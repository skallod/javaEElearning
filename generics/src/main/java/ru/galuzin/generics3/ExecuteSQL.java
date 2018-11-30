package ru.galuzin.generics3;

import java.util.List;

public interface ExecuteSQL<T> {
    List<T> exec(TransactionContext txContext);
}
