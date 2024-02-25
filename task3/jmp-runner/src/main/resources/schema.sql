drop table if exists "subscription" cascade 
drop table if exists "user" cascade 
drop sequence if exists "subscription_seq"
drop sequence if exists "user_seq"
create sequence "subscription_seq" start with 100 increment by 50
create sequence "user_seq" start with 100 increment by 50
create table "subscription" ("start_date" date, "id" bigint not null, "user_id" bigint, primary key ("id"))
create table "user" ("birthday" date, "id" bigint not null, "name" varchar(255), "surname" varchar(255), primary key ("id"))
alter table if exists "subscription" add constraint "FKbf8h5kfsyuiwhvowaq1tpkglr" foreign key ("user_id") references "user"