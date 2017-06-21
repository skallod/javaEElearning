package ru.galuzin.payment_system.common_types;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by galuzin on 26.05.2017.
 */
@MappedSuperclass
public abstract class BaseAssetData {
    @Id
    private String uid;
}
