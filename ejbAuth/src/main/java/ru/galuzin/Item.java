package ru.galuzin;

/**
 * Created by galuzin on 22.11.2016.
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Item {
    @Id
    protected Long itemId;
    protected String title;
    @ManyToMany(mappedBy="items")
    protected Set<Category> categories;
}
