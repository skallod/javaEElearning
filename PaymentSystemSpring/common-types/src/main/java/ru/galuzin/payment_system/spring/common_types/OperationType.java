package ru.galuzin.payment_system.spring.common_types;

/**
 * Created by LEONID on 14.02.2016.
 */
public enum OperationType {
    /**
     * перевод денег с счета на счет
     */
    MONEY_ORDER,
    /**
     * положить деньги на счет с терминала(банкомата)
     */
    PUT_MONEY,
    /**
     * снять деньги со счета с терминала(банкомата)
     */
    GET_MONEY
}
