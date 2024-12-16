select * from table_booking
where booking_id in (select booking_id from book where user_id = :id)

# 1. User make a booking table called table_booking
call create_booking_table(1234, 3850, 1424, 'John', '0919', '09-02-03', 50, 'Hello world');

# 2. User view order base on status
select * from table_booking
where booking_id in (select booking_id from book where user_id = 3850 and booking_status = ?);

# 3. User view detail response wheither accept or denied
-- //To do

# 4. Admin view order
call GetOrderIncome(6441)//admin_user_id

# 5. Admin decide a booking table and view it
insert into booking_decisions (booking_id, admin_user_id, admin_status, admin_note, decision_date)
values (1412, 6441, 'ACCEPTED', 'At room 4', CURRENT_TIMESTAMP);
select * from booking_decisions
where admin_user_id = 6441;
# create trigger after insert into booking_decisions change status in account booking_table