create table user
(
    id int auto_increment primary key not null,
  account_id   varchar(100),
  name         varchar(50),
  avatr        varchar(150),
  token        char(36),
  gmt_create   bigint,
  gmt_modified bigint,
  bio          varchar(256)
);

