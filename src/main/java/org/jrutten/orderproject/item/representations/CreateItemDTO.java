package org.jrutten.orderproject.item.representations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateItemDTO  {
    String name;
    String description;
    double price;
    int stock;
}
