# --- !Ups

INSERT INTO user (
    id,
    email,
    fullname,
    confirmation_token,
    password_hash,
    date_creation,
    validated)
VALUES
    (user_seq.nextval,
    'qvuong2007@gmail.com',
    'Vuong Nguyen',
    'aa5ccfee-d15e-4751-91b6-7dd1664f92af',
    '$2a$10$DXIRs.dKiY89z6GXUX0k.eOmCjDgCAx6GBcQqznGzzjhQO39ROzMu',
    NOW(),
    1),
    (user_seq.nextval,
    'admin@gmail.com',
    'Admin',
    'c4210a7a-7ca1-4ff7-931d-6cc5fb9cfa29',
    '$2a$10$5xl5Bry4SFMKSpUN/8JKEeHcksAv7mVky3ZuxYVVh04RCa1He.yN6',
    NOW(),
    1),
    (user_seq.nextval,
    'vuongnq.09@gmail.com',
    'Vuong',
    '3bb4ae0f-7117-4ac0-80ad-0fb4907bbbd2',
    '$2a$10$okiTL6nA5yqYU2IemqlWiulo2HYp646p3MAFQr/IOJ3Utt2uGrfQu',
    NOW(),
    1)
    ;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

delete from user;

SET REFERENTIAL_INTEGRITY TRUE;