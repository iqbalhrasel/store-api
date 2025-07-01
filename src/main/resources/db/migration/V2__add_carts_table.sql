create table carts(
	id binary(16) default (uuid_to_bin(uuid())) not null,
	date_created date default (CURDATE()) not null,
	primary key(id)
);

create table cart_items(
	id bigint auto_increment,
	cart_id binary(16) not null,
	product_id bigint not null,
	quantity int default 1 not null,
	primary key(id),
	unique (cart_id, product_id),
	foreign key(cart_id) references carts(id) on delete cascade,
	foreign key (product_id) references products(id) on delete cascade
);