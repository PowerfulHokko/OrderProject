-- private final String orderId;
--     private final String customerId;
--     private final List<OrderedItems> orderedItemsList;
--     private final LocalDate shippingDate;
--     private final double amount;

CREATE TABLE IF NOT EXISTS customers(
     id serial,
     first_name varchar(25),
     last_name varchar(25),
     email varchar(50) UNIQUE NOT NULL ,

     street varchar(50) NOT NULL ,
        street_number int NOT NULL,
         bus varchar(6),
         postalcode varchar(20) NOT NULL,
         city varchar(20) NOT NULL,

     PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS orders(
    id serial,
    fk_customer_id int,
    shipping_date DATE,
    total_cost double precision,
        constraint cost_constraint check (total_cost >=0),

    CONSTRAINT fk_customer_id foreign key(fk_customer_id) REFERENCES customers(id),


    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS ordered_items(
    id serial,
    fk_order_id int,
    fk_item_id int,
    qty int,
    cost_per_piece double precision,

    CONSTRAINT items_in_order_cannot_be_duplicate UNIQUE (fk_order_id, fk_item_id),
    CONSTRAINT fk_order_id foreign key(fk_order_id) REFERENCES orders(id),
    CONSTRAINT fk_item_id foreign key(fk_item_id) REFERENCES items(id),

    primary key (id)
);
