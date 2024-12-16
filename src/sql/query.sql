select  * from restaurant;
select  * from category;
select  * from restaurant_category;
--Query restaurant base on the category
SELECT  view_restaurant.* FROM view_restaurant
WHERE view_restaurant.restaurant_id
in (SELECT restaurant_id FROM restaurant_category
WHERE category_id = (SELECT category_id FROM category WHERE category_name = 'Fast Food'));
--Query restaurant base on category_id
select  view_restaurant.* from view_restaurant
where view_restaurant.restaurant_id in (SELECT restaurant_id FROM restaurant_category where category_id = 'cafe');
-- Query find category_id
SELECT * FROM category
-- Query find restaurant base on district
SELECT * FROM view_restaurant WHERE restaurant_district = '1';
