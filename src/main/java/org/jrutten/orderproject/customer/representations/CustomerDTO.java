package org.jrutten.orderproject.customer.representations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {
    int id;
    String firstName;
    String lastName;
    String email;
    Address address;
}
