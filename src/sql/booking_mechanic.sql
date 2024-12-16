# Handle booking from account
# account add booking
#Owner insert booking_decissions
# 1. Add owner of restaurant
# 2. User make a booking
# 3. Admin view owner_bookings can reply the bookings
# 4. User delete bookings
# 5.
CREATE TABLE bookings (
                          booking_id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL,
                          restaurant_id INT NOT NULL,
                          booking_date TIMESTAMP NOT NULL,
                          num_of_guests INT,
                          status ENUM('PENDING', 'ACCEPTED', 'DENIED', 'POSTPONED') DEFAULT 'PENDING',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES account(user_id),
                          CONSTRAINT fk_res_id FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id)
);
# When account insert the booking will create a decision for admin to view, edit
CREATE TRIGGER insert_booking_decision
    AFTER INSERT ON bookings
    FOR EACH ROW
BEGIN
    -- Automatically insert an initial booking decision for new bookings
    INSERT INTO booking_decisions (booking_id, admin_user_id, status, admin_note, decision_date)
    SELECT NEW.booking_id, o.user_id, 'PENDING', 'Initial decision by system', CURRENT_TIMESTAMP
    FROM ownRestaurant o
    WHERE o.restaurant_id = NEW.restaurant_id;
END;

# Handle booking_decisions where account will make a decession add reply response base booking_id from bookings
CREATE TABLE booking_decisions (
                                   decision_id SERIAL PRIMARY KEY,
                                   booking_id INT NOT NULL,
                                   admin_user_id INT NOT NULL,
                                   status ENUM('ACCEPTED', 'DENIED', 'POSTPONED') NOT NULL,
                                   admin_note TEXT,
                                   decision_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   CONSTRAINT fk_booking FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
                                   CONSTRAINT fk_admin FOREIGN KEY (admin_user_id) REFERENCES ownRestaurant(user_id)
);
# Who own the restaurant
create table ownRestaurant(
                              user_id INT NOT NULL ,
                              restaurant_id INT NOT NULL,
                              CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES account(user_id),
                              CONSTRAINT fk_res FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Timestamp of when the booking was made
);

-- Create view for bookings where the account is also the owner of the restaurant
CREATE VIEW owner_bookings AS
SELECT b.booking_id, b.user_id, b.restaurant_id, b.booking_date, b.num_of_guests, b.status, b.created_at, b.updated_at
FROM bookings b JOIN ownRestaurant s
ON b.restaurant_id = s.restaurant_id;
-- Admin update the status also status in booking
CREATE TRIGGER update_booking_status
    AFTER INSERT ON booking_decisions
    FOR EACH ROW
BEGIN
    UPDATE bookings
    SET status = NEW.status
    WHERE booking_id = NEW.booking_id;
END;
# Delete booking
CREATE TRIGGER delete_booking
    AFTER DELETE ON bookings
    FOR EACH ROW
    DELETE FROM booking_decisions
    WHERE booking_id = OLD.booking_id;

# Insert restaurant and the owener of the restaurant
-- Trigger to update booking status based on decision