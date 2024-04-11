-- User's tables
create table if not exists "user"(
    id uuid primary key not null,
    name varchar(50) not null,
    phone varchar(15) not null,
    email varchar(320),
    date_of_birth date,
    gender varchar(50),
    role varchar(50) not null
);

create table if not exists "card"(
    id uuid primary key not null,
    user_id uuid,
    number varchar(16) not null,
    type varchar(50) not null,
    discount float not null,
    foreign key (user_id) references "user"(id) on delete cascade
);

-- Restaurant's + product's table
create table if not exists "restaurant"(
    id uuid primary key not null,
    address varchar(100) not null,
    phone varchar(15) not null,
    opening_hours_from timestamp not null,
    opening_hours_to timestamp not null
);

create table if not exists "category"(
    id uuid primary key not null,
    name varchar(50) not null
);

create table if not exists "product"(
    id uuid primary key not null,
    category_id uuid not null,
    name varchar(50) not null,
    price int not null,
    weight int,
    composition varchar(300),
    description varchar(300)
);

create table if not exists "menu"(
    restaurant_id uuid not null,
    product_id uuid not null,
    foreign key (restaurant_id) references "restaurant"(id),
    foreign key (product_id) references "product"(id)
);