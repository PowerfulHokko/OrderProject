package org.jrutten.orderproject.order.representations;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "orders_id_seq")
    private int id;

    @Column(name = "fk_customer_id")
    private int customerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<OrderedItems> orderedItemsList;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "total_cost")
    private double amount;

    public Order(int customerId, List<OrderedItems> orderedItemsList, LocalDate shippingDate) {
        this.customerId = customerId;
        this.orderedItemsList = orderedItemsList;
        this.shippingDate = shippingDate;
        this.amount = calculateTotalPrice(orderedItemsList);
    }

    private static double calculateTotalPrice(List<OrderedItems> orderList) {
        return orderList.stream().mapToDouble(item -> item.getQuantity() * item.getCostPerPiece()).sum();
    }

}
