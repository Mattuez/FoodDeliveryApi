ALTER TABLE restaurant
    add opened bit not null;

update restaurant set opened = true;