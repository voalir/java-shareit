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
insert into bookings (start_date, end_date, item_id, booker_id, status)
values ('2020-01-17 23:15:41', '2027-01-17 23:15:41', 1, 2, 'APPROVED'),
('2020-01-17 23:15:42', '2020-01-17 23:15:42', 1, 2, 'WAITING'),
('2024-01-17 23:15:43', '2024-01-17 23:15:43', 1, 2, 'WAITING'),
('2020-01-17 23:15:44', '2020-01-17 23:15:44', 1, 2, 'WAITING'),
('2020-01-17 23:15:45', '2020-01-17 23:15:45', 1, 2, 'REJECTED');