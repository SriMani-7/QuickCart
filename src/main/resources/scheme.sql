CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(8) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(255) NOT NULL DEFAULT 'ALLOWED',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
)

CREATE TABLE `buyers` (
  `user_id` bigint NOT NULL,
  `city` varchar(100) NOT NULL,
  `pincode` varchar(6) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `phone_number` (`phone_number`),
  CONSTRAINT `fk_buyer_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE `retailers` (
  `user_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `address` text,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `contact_email` (`contact_email`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `category` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `retailer_id` bigint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_retailer` (`retailer_id`),
  CONSTRAINT `fk_retailer` FOREIGN KEY (`retailer_id`) REFERENCES `retailers` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE `reviews` (
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `message` text,
  `rating` double NOT NULL,
  PRIMARY KEY (`product_id`,`user_id`),
  KEY `fk_review_user_idx` (`user_id`),
  CONSTRAINT `fk_review_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `buyers` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_item_buyer` (`buyer_id`),
  KEY `fk_cart_item_product` (`product_id`),
  CONSTRAINT `fk_cart_item_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `buyers` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_method` varchar(50) NOT NULL,
  `shipping_address` varchar(255) NOT NULL,
  `city` varchar(100) NOT NULL,
  `pincode` int NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`order_id`),
  KEY `orders_ibfk_1_idx` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `buyers` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `order_items` (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`order_item_id`),
  KEY `order_items_ibfk_1` (`order_id`),
  KEY `order_items_ibfk_2` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)

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


