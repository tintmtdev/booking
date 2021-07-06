
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(1, 'Empf1', 'Empl1', 'empl1@email.com', '1991-01-01', 'SSE', 0);
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(2, 'Empf2', 'Empl12', 'empl2@email.com', '1991-01-01', 'SSE', 0);
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(3, 'Empf3', 'Empl14', 'empl3@email.com', '1991-01-01', 'SSE', 0);
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(4, 'Empf14', 'Empl14', 'empl4@email.com', '1991-01-01', 'SSE', 1);
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(5, 'Empf15', 'Empl15', 'empl5@email.com', '1991-01-01', 'SSE', 0);
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(6, 'Empf16', 'Empl16', 'empl7@email.com', '1991-01-01', 'SSE', 1);
insert into employee(id, first_name, last_name, email, birth_date, department, status) values
(7, 'Empf17', 'Empl17', 'empl7@email.com', '1991-01-01', 'SSE', 0);

insert into meeting_room(id, name, status) values (1, 'Blue', 0);
insert into meeting_room(id, name, status) values (2, 'Red', 1);
insert into meeting_room(id, name, status) values (3, 'Yellow', 0);
insert into meeting_room(id, name, status) values (4, 'Green', 0);
insert into meeting_room(id, name, status) values (5, 'White', 0);

insert into booking_room(employee_id, meeting_room_id, start_date, end_date, status) values
(1, 1, '2021-07-05 09:00:00', '2021-07-05 09:59:59', 0);
insert into booking_room(employee_id, meeting_room_id, start_date, end_date, status) values
(1, 3, '2021-07-05 11:00:00', '2021-07-05 11:29:59', 0);
insert into booking_room(employee_id, meeting_room_id, start_date, end_date, status) values
(2, 3, '2021-07-05 13:00:00', '2021-07-05 13:59:59', 3);
insert into booking_room(employee_id, meeting_room_id, start_date, end_date, status) values
(3, 3, '2021-07-05 13:00:00', '2021-07-05 13:59:59', 0);
insert into booking_room(employee_id, meeting_room_id, start_date, end_date, status) values
(5, 4, '2021-07-05 13:00:00', '2021-07-05 13:59:59', 0);