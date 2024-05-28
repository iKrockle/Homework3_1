CREATE TABLE public.car
(
    id integer,
    mark text,
    model text,
    price numeric,
    CONSTRAINT car_pk PRIMARY KEY (id)
);

CREATE TABLE public.person
(
    id integer,
    name text,
    age integer,
    drive_pass boolean,
    car_id integer not null REFERENCES public.car (id),
    CONSTRAINT person_pk PRIMARY KEY (id)
);