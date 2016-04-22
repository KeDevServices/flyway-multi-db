create table shopping_cart(
  user_id char(36) not null,
  item_id char(36) not null,
  amont integer not null,
  primary key(user_id, item_id)
);
