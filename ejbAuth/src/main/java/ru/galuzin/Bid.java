package ru.galuzin;

/**
 * Created by galuzin on 22.11.2016.
 */

public class Bid {
    private Long id;
    private Item item;
    private Bidder bidder;
    private Double amount;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Bidder getBidder() {
        return bidder;
    }
    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", item=" + item +
                ", bidder=" + bidder +
                ", amount=" + amount +
                '}';
    }
}
