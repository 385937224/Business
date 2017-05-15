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
   infoId               varchar(100) not null,
   infoType             varchar(10),
   title                varchar(30),
   source               varchar(100),
   content              text,
   memo                 varchar(300),
   creator              varchar(10),
   createTime           timestamp,
   state                varchar(1),
   primary key (infoId)
);
  
/*==============================================================*/

ROP TABLE IF EXISTS complain;

DROP TABLE IF EXISTS replyToComp;

/*==============================================================*/
/* Table: complain                                              */
/*==============================================================*/
CREATE TABLE complain
(
   compId               VARCHAR(32) NOT NULL,
   title                VARCHAR(100),
   deptOfPeople         VARCHAR(10),
   people               VARCHAR(10),
   compTime             DATE,
   state                VARCHAR(1),
   compContent          TEXT,
   complainant          VARCHAR(10),
   compCompany          VARCHAR(50),
   compMobile           VARCHAR(13),
   anonymity            BOOLEAN,
   PRIMARY KEY (compId)
);

/*==============================================================*/
/* Table: replyToComp                                           */
/*==============================================================*/
CREATE TABLE replyToComp
(
   replyId              VARCHAR(32) NOT NULL,
   compId               VARCHAR(32),
   replyTime            DATE,
   replyDept            VARCHAR(10),
   replyPeople          VARCHAR(10),
   replyContent         TEXT,
   PRIMARY KEY (replyId)
);

/* “FK_Relationship_1”  若同一个数据库中有同名的约束命名  会导致后面的约束条件创建不成功。                                          */
ALTER TABLE replyToComp ADD CONSTRAINT FK_comp_reply FOREIGN KEY (compId)
      REFERENCES complain (compId) ON DELETE RESTRICT ON UPDATE RESTRICT;


      