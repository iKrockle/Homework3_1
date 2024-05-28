alter table student ADD CONSTRAINT age_constraint CHECK (age >= 16);
alter table student ALTER COLUMN name set not null;
alter table student ADD CONSTRAINT name_unique UNIQUE (name);
alter table faculty ADD CONSTRAINT color_name_uniq UNIQUE (name,color);
alter table student ALTER COLUMN age set default 20;