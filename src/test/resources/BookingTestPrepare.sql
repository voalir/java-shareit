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
insert into items (name, description, is_available, owner_id, request_id)
values ('item1 name', 'item1 description', true, 1, null),
('item2 name', 'item2 description', false, 2, null),
('item3 name', 'item3 description', true, 2, 1);