package org.jrutten.orderproject.order.representations;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jrutten.orderproject.item.representations.Item;

import javax.persistence.*;

@Entity
@Table(name = "ordered_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedItems {

    @Id
    @SequenceGenerator(name = "ordered_items_id_seq", sequenceName = "ordered_items_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "ordered_items_id_seq")
    int id;

    @Column(name = "fk_order_id")
    int fkOrderId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_item_id")
    Item item;

    @Column(name = "qty")
    int quantity;

    @Column(name = "cost_per_piece")
    double costPerPiece;

    public OrderedItems(int fkOrderId, Item item, int quantity, double costPerPiece) {
        this.fkOrderId = fkOrderId;
        this.item = item;
        this.quantity = quantity;
        this.costPerPiece = costPerPiece;
    }

    public OrderedItems(Item item, int quantity, double costPerPiece) {
        this.item = item;
        this.quantity = quantity;
        this.costPerPiece = costPerPiece;
    }
}
