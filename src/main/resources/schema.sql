CREATE TABLE users (
    id int primary key,
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255),
    creation_date datetime,
    last_login datetime,
    access_level smallint,
    user_state varchar(255)
);