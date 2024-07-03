create table if not exists "card" (
    id uuid primary key not null,
    user_id uuid not null,
    number varchar(16) unique not null,
    type varchar(50) not null,
    discount float(1)
)