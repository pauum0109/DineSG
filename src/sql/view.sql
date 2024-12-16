create view  view_restaurant as SELECT restaurant_id,restaurant_name,phone_number,restaurant_address,restaurant_district,restaurant_image, TIME_FORMAT(open_time, '%H:%i')
    AS open_time, TIME_FORMAT(close_time, '%H:%i') AS close_time FROM restaurant;

create view  view_account as SELECT user_id,username,user_password,user_email,user_role  , DATE_FORMAT(created_at, '%d-%m-%Y') AS created_at FROM account