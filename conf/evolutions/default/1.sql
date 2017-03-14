# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table matching_info (
  id                        bigint auto_increment not null,
  requested_user_id         bigint,
  incoming_request_id       bigint,
  matched_user_id           bigint,
  created_date              datetime(6),
  updated_date              datetime(6),
  active                    tinyint(1) default 0,
  user_id                   bigint,
  constraint pk_matching_info primary key (id))
;

create table token (
  token                     varchar(255) not null,
  user_id                   bigint,
  type                      varchar(8),
  date_creation             datetime(6),
  email                     varchar(255),
  constraint ck_token_type check (type in ('password','email')),
  constraint pk_token primary key (token))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  fullname                  varchar(255),
  confirmation_token        varchar(255),
  password_hash             varchar(255),
  date_creation             datetime(6),
  validated                 tinyint(1) default 0,
  constraint uq_user_email unique (email),
  constraint uq_user_fullname unique (fullname),
  constraint pk_user primary key (id))
;

create table user_preferences (
  preference_id             bigint auto_increment not null,
  location_pref             varchar(255),
  gender_pref               varchar(255),
  student_pref              varchar(255),
  id                        bigint,
  user_id                   bigint,
  constraint uq_user_preferences_id unique (id),
  constraint uq_user_preferences_user_id unique (user_id),
  constraint pk_user_preferences primary key (preference_id))
;

create table user_profile (
  profile_id                bigint auto_increment not null,
  name                      varchar(255),
  gender                    varchar(255),
  age                       varchar(255),
  image                     longblob,
  bio                       varchar(255),
  id                        bigint,
  user_id                   bigint,
  constraint uq_user_profile_id unique (id),
  constraint uq_user_profile_user_id unique (user_id),
  constraint pk_user_profile primary key (profile_id))
;

alter table user_preferences add constraint fk_user_preferences_user_1 foreign key (id) references user (id) on delete restrict on update restrict;
create index ix_user_preferences_user_1 on user_preferences (id);
alter table user_profile add constraint fk_user_profile_user_2 foreign key (id) references user (id) on delete restrict on update restrict;
create index ix_user_profile_user_2 on user_profile (id);


create index ix_matching_info_user_id_3 on matching_info(user_id);

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table matching_info;

drop table token;

drop table user;

drop table user_preferences;

drop table user_profile;

SET FOREIGN_KEY_CHECKS=1;

