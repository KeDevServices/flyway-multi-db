/*[postgres:start]*/
alter table shopping_cart rename column amont to amount;
/*[postgres:end]*/

/*[h2:start]*/
alter table shopping_cart alter column amont rename to amount;
/*[h2:end]*/
