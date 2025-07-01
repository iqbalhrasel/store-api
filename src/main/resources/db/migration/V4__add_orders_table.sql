create table orders(
    id bigint auto_increment,
    customer_id bigint not null,
    status varchar(20) not null,
    created_at datetime default current_timestamp not null,
    total_price decimal(10,2) not null,
    primary key(id),
    foreign key(customer_id) references users(id)
);

create table order_items(
    id bigint auto_increment,
    order_id bigint not null,
    product_id bigint not null,
    unit_price decimal(10,2) not null,
    quantity int not null,
    total_price decimal(10,2) not null,
    primary key(id),
    foreign key(order_id) references orders(id),
    foreign key(product_id) references products(id)
);