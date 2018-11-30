package ru.galuzin.generics3;

import java.sql.*;
import java.util.*;

public class PreparedExecutor {
    <T> List<T> execute(ItemHandler<T> rh, TransactionContext txContext){
        ArrayList<T> arrayList = new ArrayList<>();
        String result = "mars temperature 50 via " + txContext.getConnectionID()+".";
        try {
            T handle = rh.handle(result);
            arrayList.add(handle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
