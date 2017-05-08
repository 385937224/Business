/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/5/4 14:20:51                            */
/*==============================================================*/


drop table if exists role;

drop table if exists role_privilege;

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   roleId               varchar(100) not null,
   name                 varchar(20),
   state                varchar(1),
   primary key (roleId)
);

/*==============================================================*/
/* Table: role_privilege                                        */
/*==============================================================*/
create table role_privilege
(
   roleId               varchar(100) not null,
   code                 varchar(20) not null,
   primary key (roleId, code)
);

alter table role_privilege add constraint FK_Relationship_1 foreign key (roleId)
      references role (roleId) on delete restrict on update restrict;
      
      
      
drop table if exists info;

/*==============================================================*/
/* Table: info                                                  */
/*==============================================================*/
create table info
(
   infeId               varchar(100) not null,
   infoType             varchar(10),
   title                varchar(30),
   source               varchar(100),
   content              text,
   memo                 varchar(300),
   creator              varchar(10),
   createTime           timestamp,
   state                varchar(1),
   primary key (infeId)
);
      
      
      