create table COMMENT
(
  ID            BIGINT  auto_increment
    primary key,
  PARENT_ID     BIGINT  not null,
  TYPE          INTEGER not null,
  COMMENTATOR   INTEGER not null,
  GMT_CREATE    BIGINT  not null,
  GMT_MODIFIED  BIGINT  not null,
  LIKE_COUNT    BIGINT default 0,
  CONTENT       VARCHAR(1024),
  COMMENT_COUNT INTEGER
);
create table NOTIFICATION
(
  ID            BIGINT   auto_increment
    primary key,
  NOTIFIER      BIGINT            not null,
  RECEIVER      BIGINT            not null,
  OUTERID       BIGINT            not null,
  TYPE          INTEGER           not null,
  GMT_CREATE    BIGINT            not null,
  STATUS        INTEGER default 0 not null,
  NOTIFIER_NAME VARCHAR(100),
  OUTER_TITLE   VARCHAR(256)
);

create table QUESTION
(
  ID            INTEGER  auto_increment
    primary key,
  TITLE         VARCHAR(50),
  DESCRIPTION   CLOB,
  GMT_CREATE    DATE,
  GMT_MODIFIED  BIGINT,
  CREATOR       INTEGER,
  COMMENT_COUNT INTEGER default 0,
  VIEW_COUNT    INTEGER default 0,
  LIKE_COUNT    INTEGER default 0,
  TAG           VARCHAR(256)
);

create table USER
(
  ID           INTEGER  auto_increment,
  ACCOUNT_ID   VARCHAR(100),
  NAME         VARCHAR(50),
  TOKEN        CHAR(36),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT,
  BIO          VARCHAR(256),
  AVATAR_URL   VARCHAR(100),
  TYPE         INTEGER,
  constraint USER_PK
    primary key (ID)
);

