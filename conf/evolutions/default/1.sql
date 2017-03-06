# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table matching_information (
  id                        bigint not null,
  requested_user_id         bigint,
  incoming_request_id       bigint,
  matched_user_id           bigint,
  user_id                   bigint not null,
  active                    boolean,
  constraint pk_matching_information primary key (id))
;

create table token (
  token                     varchar(255) not null,
  user_id                   bigint,
  type                      varchar(8),
  date_creation             timestamp,
  email                     varchar(255),
  constraint ck_token_type check (type in ('password','email')),
  constraint pk_token primary key (token))
;

create table user (
  id                        bigint not null,
  email                     varchar(255),
  fullname                  varchar(255),
  confirmation_token        varchar(255),
  password_hash             varchar(255),
  date_creation             timestamp,
  validated                 boolean,
  constraint uq_user_email unique (email),
  constraint uq_user_fullname unique (fullname),
  constraint pk_user primary key (id))
;

create table user_preferences (
  preference_id             bigint not null,
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
  profile_id                bigint not null,
  name                      varchar(255),
  gender                    varchar(255),
  age                       varchar(255),
  image                     blob,
  bio                       varchar(255),
  id                        bigint,
  user_id                   bigint,
  constraint uq_user_profile_id unique (id),
  constraint uq_user_profile_user_id unique (user_id),
  constraint pk_user_profile primary key (profile_id))
;

create sequence matching_information_seq;

create sequence token_seq;

create sequence user_seq;

create sequence user_preferences_seq;

create sequence user_profile_seq;

alter table matching_information add constraint fk_matching_information_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_matching_information_user_1 on matching_information (user_id);
alter table user_preferences add constraint fk_user_preferences_user_2 foreign key (id) references user (id) on delete restrict on update restrict;
create index ix_user_preferences_user_2 on user_preferences (id);
alter table user_profile add constraint fk_user_profile_user_3 foreign key (id) references user (id) on delete restrict on update restrict;
create index ix_user_profile_user_3 on user_profile (id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists matching_information;

drop table if exists token;

drop table if exists user;

drop table if exists user_preferences;

drop table if exists user_profile;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists matching_information_seq;

drop sequence if exists token_seq;

drop sequence if exists user_seq;

drop sequence if exists user_preferences_seq;

drop sequence if exists user_profile_seq;

