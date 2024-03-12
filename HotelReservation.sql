create database reservation;

use reservation;

create table hotel(reservation_id int auto_increment primary key,
guest_name varchar(50) not null,
room_no int not null unique,
mobile_no varchar(10) not null,
reservation_date timestamp default current_timestamp);
