alter table restaurant
    add active bit not null;

update restaurant set active = true;