
CREATE TABLE account (
                      user_id INT NOT NULL PRIMARY KEY,
                      username VARCHAR(20) NOT NULL,
                      user_password VARCHAR(50) NOT NULL,
                      user_email VARCHAR(50) NOT NULL,

                      user_role VARCHAR(50) NOT NULL,
                      created_at DATE DEFAULT (CURRENT_DATE) NULL,
                      CONSTRAINT user_email_unique UNIQUE (user_email)
);
create table otp (
                     user_id int not null primary key,
                     otp varchar(10) not null,
                     created_at timestamp default CURRENT_TIMESTAMP null,
                     constraint otp_ibfk_1 foreign key (user_id) references account (user_id)
);
create table article (
                         article_id int not null primary key,
                         title varchar(100) null,
                         content text null,
                         image varchar(100) null
);


-- Tạo view_restaurant lại với district name
-- Insert table district
create table restaurant (
                            restaurant_id int not null primary key,
                            restaurant_name varchar(100) null,
                            restaurant_district varchar(20) null,
                            restaurant_address varchar(100) null,
                            restaurant_image varchar(100) null,
                            phone_number varchar(12) null,
                            open_time time null,
                            close_time time null,
                            constraint restaurant_category_district foreign key (restaurant_district) references district(district_id));

create table category (
                          category_id varchar(20) primary key,
                          category_name varchar(20) UNIQUE not null,
                          category_image varchar(20) null
);
create table restaurant_category (
                                     restaurant_id int ,
                                     category_id varchar(20)  ,
                                    constraint restaurant_category_ibfk_1 foreign key (restaurant_id) references restaurant (restaurant_id),
                                    constraint restaurant_category_ibfk_2 foreign key (category_id) references category (category_id),
                                    primary key (restaurant_id, category_id)
);

create index restaurant_id on restaurant_category (restaurant_id);
create table district(
                        district_id int primary key AUTO_INCREMENT,
                        district_name varchar(10) UNIQUE not null
);
CREATE TABLE district (
                          district_id VARCHAR(20) PRIMARY KEY,
                          district_name VARCHAR(20) UNIQUE NOT NULL
) AUTO_INCREMENT=1;

create table bookTable (
                           book_id int not null primary key,
                           book_date date null,
                           book_time time null,
                           book_number int null,
                           book_name varchar(100) null,
                           book_phone varchar(12) null,
                           book_email varchar(50) null,
                           book_message varchar(100) null,
                           restaurant_id int not null,
                           constraint bookTable_ibfk_1 foreign key (restaurant_id) references restaurant (restaurant_id));
create table customerBook (
                              user_id int not null ,
                              book_id int not null primary key,
                              created_at DATE DEFAULT (CURRENT_DATE) NULL,
                              constraint customerBook_ibfk_1 foreign key (user_id) references account (user_id),
                              constraint customerBook_ibfk_2 foreign key (book_id) references bookTable (book_id));

--
create table spring_session
(
    PRIMARY_ID            char(36)     not null
        primary key,
    SESSION_ID            char(36)     not null,
    CREATION_TIME         bigint       not null,
    LAST_ACCESS_TIME      bigint       not null,
    MAX_INACTIVE_INTERVAL int          not null,
    EXPIRY_TIME           bigint       not null,
    PRINCIPAL_NAME        varchar(100) null,
    constraint SPRING_SESSION_IX1
        unique (SESSION_ID)
)
    row_format = DYNAMIC;

create index SPRING_SESSION_IX2
    on spring_session (EXPIRY_TIME);

create index SPRING_SESSION_IX3
    on spring_session (PRINCIPAL_NAME);
--
create table spring_session_attributes
(
    SESSION_PRIMARY_ID char(36)     not null,
    ATTRIBUTE_NAME     varchar(200) not null,
    ATTRIBUTE_BYTES    blob         not null,
    primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK
        foreign key (SESSION_PRIMARY_ID) references spring_session (PRIMARY_ID)
            on delete cascade
)
    row_format = DYNAMIC;