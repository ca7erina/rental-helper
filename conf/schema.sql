set global max_connections = 2000;
create database rentalhelper;

use rentalhelper;

create table matching_info (
  id                        bigint not null AUTO_INCREMENT,
  requested_user_id         bigint,
  incoming_request_id       bigint,
  matched_user_id           bigint,
  created_date              timestamp,
  updated_date              timestamp,
  active                    boolean,
  user_id                   bigint,
  constraint pk_matching_info primary key (id))
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
  id                        bigint not null AUTO_INCREMENT,
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
  preference_id             bigint not null AUTO_INCREMENT,
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
  profile_id                bigint not null AUTO_INCREMENT,
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



alter table user_preferences add constraint fk_user_preferences_user_1 foreign key (id) references user (id) on delete restrict on update restrict;
create index ix_user_preferences_user_1 on user_preferences (id);
alter table user_profile add constraint fk_user_profile_user_2 foreign key (id) references user (id) on delete restrict on update restrict;
create index ix_user_profile_user_2 on user_profile (id);
create index ix_matching_info_user_id_3 on matching_info(user_id);

INSERT INTO user (
    email,
    fullname,
    confirmation_token,
    password_hash,
    date_creation,
    validated)
VALUES
    (
    'qvuong2007@gmail.com',
    'Vuong Nguyen',
    'aa5ccfee-d15e-4751-91b6-7dd1664f92af',
    '$2a$10$DXIRs.dKiY89z6GXUX0k.eOmCjDgCAx6GBcQqznGzzjhQO39ROzMu',
    NOW(),
    1),
    (
    'admin@gmail.com',
    'Admin',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'admin2@gmail.com',
    'Admin 2',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'admin3@gmail.com',
    'Admin 3',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'admin4@gmail.com',
    'Admin 4',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'vuongnq.09@gmail.com',
    'Vuong',
    '3bb4ae0f-7117-4ac0-80ad-0fb4907bbbd2',
    '$2a$10$okiTL6nA5yqYU2IemqlWiulo2HYp646p3MAFQr/IOJ3Utt2uGrfQu',
    NOW(),
    1),
    (
    'a@gmail.com',
    'Andrew Comey',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'colin@gmail.com',
    'Colin Comey',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'max@gmail.com',
    'Max Comey',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'homer@gmail.com',
    'Homer Simpson',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (
    'marge@gmail.com',
    'Marge Simpson',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1)
    ;

INSERT INTO user_profile (
    name,
    gender,
    age,
    bio,
    user_id)
VALUES
    (
    'Admin',
    'Male',
    '21',
    'Hi',
    2
    ),
    (
    'Vuong',
    'Male',
    '45',
    'I like coding',
    1),
    (
    'Andrew Comey',
    'Male',
    '22',
    'Im great',
    7),
    (
    'Colin Comey',
    'Male',
    '20',
    'Im great',
    8),
    (
    'Max Comey',
    'Male',
    '12',
    'Im a dog',
    9),
    (
    'Homer Simpson',
    'Male',
    '40',
    'Doh!!',
    10),
    (
    'Marge Simpson',
    'Female',
    '40',
    'I hate Homer!!',
    11)
    ;

INSERT INTO user_preferences (
    location_pref,
    gender_pref,
    student_pref,
    user_id )
VALUES
    (
    'Dublin 1',
    'Male',
    'Student',
    7),
    (
    'Dublin 1',
    'Male',
    'Student',
    2),
    (
    'Dublin 1',
    'Male',
    'Student',
    8),
    (
    'Dublin 1',
    'Male',
    'Student',
    9),
    (
    'Dublin 1',
    'Female',
    'Professional',
    10),
    (
    'Dublin 1',
    'Male',
    'Professional',
    11);
