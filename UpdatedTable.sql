use reservation;

create table hotel(reservation_id int auto_increment primary key,
guest_name varchar(50) not null,
room_no int not null unique,
mobile_no varchar(10) not null,
reservation_date timestamp default current_timestamp,
no_of_people int not null, check ((no_of_people > 0 and no_of_people <= 4))
);
