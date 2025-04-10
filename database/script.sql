
CREATE DATABASE mvp_database;
GO

USE mvp_database;
GO

CREATE TABLE users (
    id INT PRIMARY KEY IDENTITY(1,1),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255),
    creation_date DATETIME,
    last_login DATETIME,
    access_level SMALLINT,
    user_state INT
);

CREATE TABLE product_category (
    id INT PRIMARY KEY IDENTITY(1,1),
    category_name VARCHAR(255),
    creation_date DATETIME,
    last_upt_date DATETIME,
    category_state INT
);

CREATE TABLE products (
    id INT PRIMARY KEY IDENTITY(1,1),
    product_name VARCHAR(255),
    category_id INT,
    price BIGINT,
    creation_date DATETIME,
    last_upt_date DATETIME,
    product_state INT,
    FOREIGN KEY (category_id) REFERENCES product_category(id)
);

CREATE TABLE tags (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255),
    tag_state INT DEFAULT 1
);

CREATE TABLE product_tags (
    product_id INT,
    tag_id INT,
    PRIMARY KEY (product_id, tag_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);
