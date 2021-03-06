insert into t_subscriber(
subscriber_city
,subscriber_complement
,subscriber_doc
,subscriber_email
,subscriber_name
,subscriber_number
,subscriber_street
,subscriber_zip_code)
values
('NY','West Side','99999','johnsmith@brainyit.com.br','BrainyIT','4321','Main street','999999');

insert into t_subscriber(
subscriber_city
,subscriber_complement
,subscriber_doc
,subscriber_email
,subscriber_name
,subscriber_number
,subscriber_street
,subscriber_zip_code)
values
('NY','West Side','99999','johnsmith@brainyit.com.br','Fake Company','4321','Main street','999999');

insert into t_resource(resource_name) values ('Res1');
insert into t_resource(resource_name) values ('Res2');
insert into t_resource(resource_name) values ('Res3');
insert into t_resource(resource_name) values ('Res4');
insert into t_resource(resource_name) values ('Res5');
insert into t_resource(resource_name) values ('Res6');
insert into t_resource(resource_name) values ('Res7');

insert into t_user (user_login,user_email,user_password,id_subscriber) values ('rchiarinelli','rchiarinelli@gmail.com','12345',1);
insert into t_user (user_login,user_email,user_password,id_subscriber) values ('mhnagaoka','mhnagaoka@gmail.com','12345',1);
insert into t_user (user_login,user_email,user_password,id_subscriber) values ('rbogre','rbogre@gmail.com','12345',1);
insert into t_user (user_login,user_email,user_password,id_subscriber) values ('rchiarinelli_2','rchiarinelli_2@gmail.com','12345',2);


insert into t_permission (user_login,operation_name,id_resource) values ('rchiarinelli','READ',1);
insert into t_permission (user_login,operation_name,id_resource) values ('mhnagaoka','READ',1);
insert into t_permission (user_login,operation_name,id_resource) values ('rbogre','READ',1);