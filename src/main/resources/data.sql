INSERT INTO roles(is_deleted,insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, description)
VALUES (false, '2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 'Admin'),
       (false,'2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 'Manager'),
       (false,'2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 'Employee');


INSERT INTO users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                  first_name, last_name, user_name, gender, phone, role_id)
VALUES ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'admin', 'admin', 'admin@admin.com', 'MALE', '0000000000', 1),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Harold', 'Finch', 'harold@manager.com', 'MALE', '0123456789', 2),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Samantha', 'Groves', 'samantha@manager.com', 'MALE', '9876543210', 2),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'John', 'Reese', 'john@employee.com', 'MALE', '7894561230', 3),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Sameen', 'Shaw', 'sameen@employee.com', 'MALE', '0321654987', 3),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Grace', 'Hendricks', 'grace@employee.com', 'MALE', '7410258963', 3),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Lionel', 'Fusco', 'lionel@employee.com', 'MALE', '3698520147', 3);