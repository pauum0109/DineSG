-- Description: This file contains the data to be inserted into the database
--INSERT restaurant
-- Insert into District 1
-- Hoàn thành nốt end_point
-- view_restaurant model
INSERT INTO restaurant
(restaurant_id, restaurant_name, restaurant_address,phone_number, restaurant_picture, restaurant_district, open_time, close_time)
VALUES
    (1, 'Pho 24', '123 Le Loi St, District 1','091964615', 'pho24_d1.png', '1', '08:00:00', '22:00:00');

-- Insert into District 3
INSERT INTO restaurant
(restaurant_id, restaurant_name, restaurant_address,phone_number, restaurant_picture, restaurant_district, open_time, close_time)
VALUES
    (2, 'Bun Cha Ha Noi', '45 Vo Van Tan St, District 3','091616151', 'bun_cha_d3.png', '3', '09:00:00', '21:30:00');

-- Insert into District 7
INSERT INTO restaurant
(restaurant_id, restaurant_name, restaurant_address, restaurant_picture, restaurant_district, open_time, close_time)
VALUES
    (3, 'Nha Hang Vua Cua', '12 Nguyen Van Linh St, District 7', 'vua_cua_d7.png', '7', '10:00:00', '23:00:00');

-- Insert into Thu Duc
INSERT INTO restaurant
(restaurant_id, restaurant_name, restaurant_address, restaurant_picture, restaurant_district, open_time, close_time)
VALUES
    (4, 'Com Tam Cali', '67 Quang Trung St, Thu Duc', 'com_tam_thu_duc.png', 'Thu Duc', '07:30:00', '21:00:00');
                                -- Insert category--
insert into  category (category_id,category_name, image) values ('fast_food','Fast Food', 'Fast Food.png');
insert into  category (category_id,category_name, image) values ('morning','Morning', 'Morning.png');
insert into  category (category_id,category_name, image) values ('dinner','Dinner', 'Dinner.png');
insert into  category (category_id,category_name, image) values ('cafe','cafe', 'cafe.png');
insert into  category (category_id,category_name, image) values ('bar','Bar', 'Bar.png');
-- ## Insert restaurant_category
INSERT INTO restaurant_category (restaurant_id, category_id)
VALUES (
           (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Pho 24'),
           (SELECT category_id FROM category WHERE  category_name = 'Fast Food')
       );
INSERT INTO restaurant_category (restaurant_id, category_id)
VALUES (
           (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Pho 24'),
           (SELECT category_id FROM category WHERE  category_name = 'cafe'));


INSERT INTO restaurant_category (restaurant_id, category_id)
VALUES (
           (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Com Tam Cali'),
           (SELECT category_id FROM category WHERE  category_name = 'cafe'));

INSERT INTO restaurant_category (restaurant_id, category_id)
VALUES (
           (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Pho 24'),
            'cafe' );
INSERT INTO restaurant_category (restaurant_id, category_id)
VALUES (
           (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Com Tam Cali'),
           'cafe' );
INSERT INTO restaurant_category (restaurant_id, category_id)
VALUES (
           (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Com Tam Cali'),
           'dinner' );
-- Book Table--
insert into account(user_id, username, user_password, user_email) values (1, 'admin', 'admin', 'lemanh14512@gmail.com');
insert  into booktable(book_id, book_date, book_time, book_number, book_name, book_phone, book_email, book_message, restaurant_id)
values (1, '2021-12-12', '12:00:00', 2, 'Nguyen Van A', '091616151'
       , 'abc@gmail.com','vegetarian', 1);
insert into customerBook (user_id, book_id) values (1, 1);
select  * from customerBook;
select * from booktable;