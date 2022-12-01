create table meetings
(
    id SERIAL PRIMARY KEY,
    address text NOT NULL,
    description text,
    organizer text NOT NULL,
    topic text NOT NULL,
    start_date timestamp NOT NULL
);

