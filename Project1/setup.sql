 drop table if exists ers_reimbursement_status, ers_reimbursement_type, ers_user_roles, ers_users, ers_reimbursement;
 create table if not exists ers_reimbursement_type (
 	reimb_type_id SERIAL primary key,
 	reimb_type VARCHAR(10)
 );

 create table if not exists ers_user_roles (
 	ers_user_role_id SERIAL primary key,
 	user_role VARCHAR(10)
 );

 create table if not exists ers_reimbursement_status (
 	reimb_status_id SERIAL primary key,
 	reimb_status VARCHAR(10)
 );


 create table if not exists ers_users (
 	ers_users_id SERIAL primary key,
 	ers_username VARCHAR(50) UNIQUE,
 	ers_password VARCHAR(50),
 	user_first_name VARCHAR(100),
 	user_last_name VARCHAR(100),
 	user_email VARCHAR(150) UNIQUE,
 	user_role_id INTEGER references ers_user_roles(ers_user_role_id)
 );

 create table if not exists ers_reimbursement (
 	reimb_id SERIAL primary key,
 	reimb_amount INTEGER,
 	reimb_submitted TIMESTAMP,
 	reimb_resolved TIMESTAMP,
 	reimb_description VARCHAR(250),
 	reimb_author INTEGER references ers_users(ers_users_id),
 	reimb_resolver INTEGER references ers_users(ers_users_id),
 	reimb_status_id INTEGER references ers_reimbursement_status(reimb_status_id),
 	reimb_type_id INTEGER references ers_reimbursement_type(reimb_type_id)
 );
 
insert into ers_reimbursement_type (reimb_type) values ('LODGING');
insert into ers_reimbursement_type (reimb_type) values ('TRAVEL');
insert into ers_reimbursement_type (reimb_type) values ('FOOD');
insert into ers_reimbursement_type (reimb_type) values ('OTHER');

insert into ers_user_roles (user_role) values ('Employee');
insert into ers_user_roles (user_role) values ('Manager');

insert into ers_reimbursement_status (reimb_status) values ('Pending');
insert into ers_reimbursement_status (reimb_status) values ('Approved');
insert into ers_reimbursement_status (reimb_status) values ('Deny');

--insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
--values ('Alan', 'pass', 'first', 'last', 'user@email.com', 2);
--insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
--values ('Andy', 'password', 'first', 'last', 'david@email.com', 1);