CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(8) NOT NULL,
);

CREATE TABLE buyers (
    user_id BIGINT PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    pincode VARCHAR(6) NOT NULL,
    phone_number VARCHAR(10) NOT NULL UNIQUE,
    CONSTRAINT fk_buyer_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE retailers (
    user_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    address TEXT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255),
    price DECIMAL(10, 2),
    retailer_id BIGINT,
    CONSTRAINT fk_retailer FOREIGN KEY (retailer_id) REFERENCES retailers(user_id)
);

CREATE TABLE reviews (
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    message TEXT,
    rating DOUBLE NOT NULL,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT pk_review PRIMARY KEY (product_id, user_id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES buyers(user_id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(50) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    pincode INT NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending' NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE order_items (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending' NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);




INSERT INTO users (username, email, password, role) VALUES
('john_doe', 'john.doe@example.com', '1234', 'SELLER'),
('jane_smith', 'jane.smith@example.com', '9012', 'SELLER'),
('alice_johnson', 'alice.johnson@example.com', 'njijkk', 'SELLER'),
('admin', 'admin@example.com', 'admin', 'ADMIN');


INSERT INTO sellers (user_id, name, contact_email, phone_number, address) VALUES
(1, 'John Doe Electronics', 'john.doe.electronics@example.com', '555-1234', '123 Elm Street, Springfield'),
(2, 'Jane Smith Fashion', 'jane.smith.fashion@example.com', '555-5678', '456 Oak Avenue, Metropolis'),
(3, 'Alice Johnson Furniture', 'alice.johnson.furniture@example.com', '555-8765', '789 Pine Road, Gotham');


INSERT INTO products (name, description, category, price, retailer_id) VALUES
('Gaming Laptop', 'High-performance gaming laptop with 32GB RAM and 1TB SSD', 'Electronics', 1999.99, 1),
('4K Monitor', '27-inch 4K monitor with ultra-thin bezels', 'Electronics', 499.99, 1),
('Leather Jacket', 'Genuine leather jacket with a stylish design', 'Fashion', 129.95, 2),
('Summer Dress', 'Lightweight and comfortable summer dress', 'Fashion', 49.99, 2),
('Office Desk', 'Adjustable height office desk with built-in cable management', 'Furniture', 299.50, 3),
('Ergonomic Chair', 'High-back ergonomic office chair with lumbar support', 'Furniture', 159.75, 3);


SELECT p.id, 
       p.name, 
       p.category, 
       p.price, 
       u.username AS sellerUsername, 
       COALESCE(oi.totalOrders, 0) AS totalOrders
FROM products p
left JOIN retailers r ON p.retailer_id = r.user_id
left JOIN users u ON r.user_id = u.id
LEFT JOIN (
    SELECT oi.product_id, COUNT(*) AS totalOrders
    FROM order_items oi
    GROUP BY oi.product_id
) oi ON p.id = oi.product_id;


