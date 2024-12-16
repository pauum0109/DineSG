create table otp (
                     user_id int not null primary key,
                     otp varchar(10) not null,
                     created_at timestamp default CURRENT_TIMESTAMP null,
                     constraint otp_ibfk_1 foreign key (user_id) references account (user_id)
);

CREATE EVENT delete_expired_otps
    ON SCHEDULE EVERY 1 MINUTE
    DO
    DELETE FROM otp WHERE created_at < (NOW() - INTERVAL 15 MINUTE);
-- SELECT otp exist . If exist, return otp, else announce resend OTP again
SELECT * FROM otp
WHERE created_at >= (NOW() - INTERVAL 10 MINUTE) AND user_id = ?;
-- Trigger OTP when account update
CREATE TRIGGER update_otp_timestamp
BEFORE UPDATE ON otp
FOR EACH ROW
BEGIN
    IF NEW.otp != OLD.otp THEN
        SET NEW.created_at = NOW();
    END IF;
END;