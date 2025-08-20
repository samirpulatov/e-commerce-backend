CREATE table carts (
                       id BINARY(16) default (uuid_to_bin(uuid())) not null
                           primary key ,
                       date_created DATE default (curdate()) not null
);



CREATE table cart_items (
                            id bigint auto_increment
        primary key ,
                            cart_id binary(16) not null,
                            constraint cart_items_carts_id_fk foreign key (cart_id) references carts(id)
                                on delete cascade ,
                            product_id bigint not null ,
                            constraint cart_items_products_fk foreign key (product_id) references products(id)
                                on delete cascade ,
                            quanity int default 1 not null,
                            constraint cart_items_cart_product_unique unique (cart_id,product_id)
);



