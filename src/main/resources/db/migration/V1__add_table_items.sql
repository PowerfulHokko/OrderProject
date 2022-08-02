CREATE TABLE IF NOT EXISTS items(
    id serial,
    name varchar(50) UNIQUE NOT NULL,
    description varchar(200) default 'no description',

    price double precision not null,
        constraint price_constraint check (price>0),

    stock int not null,
        constraint  stock_constraint check(stock>=1),
    PRIMARY KEY(id)

);


