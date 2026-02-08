INSERT INTO product
(name, description, brand, category, price, product_available,
 stock_quantity, release_date, image_name, image_type, image_data)

VALUES

    ('Galaxy S21',
     SUBSTRING('The Samsung Galaxy S21 is a premium smartphone designed for high performance and reliability. It features a powerful processor, advanced camera technology, long-lasting battery life, and a sleek modern design. This device is suitable for gaming, photography, and everyday multitasking, making it ideal for both students and professionals.', 1, 255),
     'Samsung', 'Mobile',
     55000.00, TRUE, 50, '2024-01-10',
     NULL, NULL, NULL),

    ('iPhone 13',
     SUBSTRING('The iPhone 13 offers a smooth and secure user experience with Apple’s advanced A15 Bionic chip. It provides excellent camera quality, strong data security, long software support, and premium build quality. This smartphone is perfect for users who prefer stability, performance, and seamless integration with Apple products.', 1, 255),
     'Apple', 'Mobile',
     65000.00, TRUE, 40, '2024-02-15',
     NULL, NULL, NULL),

    ('Dell XPS',
     SUBSTRING('The Dell XPS laptop is built for professionals who require high-speed performance and portability. It includes a high-resolution display, powerful processor, lightweight aluminum body, and extended battery life. This laptop is suitable for programming, designing, business work, and multimedia editing.', 1, 255),
     'Dell', 'Laptop',
     95000.00, TRUE, 25, '2023-11-05',
     NULL, NULL, NULL),

    ('HP Pavilion',
     SUBSTRING('The HP Pavilion series offers a perfect balance between performance and affordability. It comes with modern hardware, comfortable keyboard, clear display, and reliable cooling system. This laptop is ideal for students, office workers, and home users who need a dependable everyday computing device.', 1, 255),
     'HP', 'Laptop',
     72000.00, TRUE, 30, '2023-12-20',
     NULL, NULL, NULL),

    ('Sony Headphones',
     SUBSTRING('Sony noise-cancelling headphones deliver immersive sound quality with advanced noise reduction technology. They provide deep bass, clear vocals, and comfortable ear cushions for long listening sessions. These headphones are perfect for travel, gaming, online meetings, and music lovers.', 1, 255),
     'Sony', 'Audio',
     12000.00, TRUE, 100, '2024-03-12',
     NULL, NULL, NULL),

    ('Boat Rockerz',
     SUBSTRING('Boat Rockerz Bluetooth headphones are designed for users who want powerful sound at an affordable price. They feature wireless connectivity, fast charging support, durable build quality, and extended battery backup. These headphones are suitable for workouts, calls, and daily entertainment.', 1, 255),
     'Boat', 'Audio',
     2500.00, TRUE, 200, '2024-04-01',
     NULL, NULL, NULL),

    ('Canon EOS',
     SUBSTRING('The Canon EOS DSLR camera is designed for professional photography and videography. It offers high-resolution image capture, advanced autofocus system, multiple shooting modes, and strong low-light performance. This camera is ideal for photographers, vloggers, and creative professionals.', 1, 255),
     'Canon', 'Camera',
     78000.00, TRUE, 15, '2023-09-18',
     NULL, NULL, NULL),

    ('Nikon D3500',
     SUBSTRING('The Nikon D3500 is an entry-level DSLR camera that provides excellent image quality and ease of use. It features a lightweight body, intuitive controls, long battery life, and reliable autofocus. This camera is suitable for beginners who want to learn professional photography.', 1, 255),
     'Nikon', 'Camera',
     52000.00, TRUE, 18, '2023-08-22',
     NULL, NULL, NULL),

    ('Lenovo ThinkPad',
     SUBSTRING('The Lenovo ThinkPad is known for its durability, security features, and business-class performance. It includes a spill-resistant keyboard, fingerprint scanner, strong build quality, and excellent multitasking capabilities. This laptop is ideal for corporate users and software developers.', 1, 255),
     'Lenovo', 'Laptop',
     68000.00, TRUE, 35, '2024-01-30',
     NULL, NULL, NULL),

    ('Redmi Note 12',
     SUBSTRING('The Redmi Note 12 offers impressive features at a budget-friendly price. It includes a large display, high-capacity battery, fast charging, and smooth performance for daily tasks. This smartphone is suitable for students and budget-conscious users who want value for money.', 1, 255),
     'Xiaomi', 'Mobile',
     15000.00, TRUE, 90, '2024-05-10',
     NULL, NULL, NULL);
