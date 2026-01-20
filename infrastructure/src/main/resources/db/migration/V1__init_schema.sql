-- Initial schema for Products and Orders
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(19, 2) NOT NULL,
                          stock INTEGER NOT NULL
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id BIGINT REFERENCES orders(id),
                             product_id BIGINT NOT NULL,
                             quantity INTEGER NOT NULL,
                             price_at_purchase DECIMAL(19, 2) NOT NULL
);