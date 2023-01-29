delete from users;
delete from requests;
delete from items;
delete from bookings;
insert into users (name, email)
values ('user1', 'user1@mail.m'),
('user2', 'user2@mail.m'),
('user3', 'user3@mail.m');
insert into requests (description, requestor_id)
values ('request', 3);