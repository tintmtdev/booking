/*Create user table*/
create sequence public.employee_id_seq
    increment 1
    start 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;

create table employee (
   id integer not null default nextval('employee_id_seq'::regclass),
   first_name text,
   last_name text,
   email text,
   birth_date date,
   department text,
   status integer,
   constraint employee_pk primary key (id)
);

/*Create meeting room*/
create sequence public.meeting_room_id_seq
    increment 1
    start 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;

create table meeting_room (
   id integer not null default nextval('meeting_room_id_seq'::regclass),
   name text,
   status integer,
   constraint meeting_room_pk primary key (id)
);

/*Create booking room*/
create sequence public.booking_room_id_seq
    increment 1
    start 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;

create table booking_room (
  id integer not null default nextval('booking_room_id_seq'::regclass),
  employee_id integer,
  meeting_room_id integer,
  start_date timestamp,
  end_date timestamp,
  status integer,
  constraint booking_room_pk primary key (id),
  constraint employee_id_fk foreign key (employee_id) references employee(id),
  constraint meeting_room_id_fk foreign key (meeting_room_id) references meeting_room(id)
);