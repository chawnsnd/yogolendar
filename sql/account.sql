select * from tbl_account;
select * from tbl_access_auth;

-- 계정 테이블 생성
create table tbl_account(
    no number(5) primary key,
    id varchar2(20) not null unique,
    password varchar2(150) not null,
    name varchar2(20) not null
);

-- 계정 시퀀스 생성
create sequence seq_account;

-- 권한 테이블 생성
create table tbl_access_auth(
    no number(5) primary key,
    client_no number(5) references tbl_account(no) ON DELETE CASCADE not null,
    server_no number(5) references tbl_account(no) ON DELETE CASCADE not null,
    todo char(1) default 1 check(todo='0' or todo='1') not null,
    anniversary char(1) default 1 check(anniversary='0' or anniversary='1') not null
);

-- 권한 시퀀스 생성
create sequence seq_access_auth;

-- hide 컬럼을 가지는 테이블 따로 만듬
create table tbl_account_option(
	no number(5) primary key,
	account_no number(5) references tbl_account(no) ON DELETE CASCADE not null,
	hide char(1) default 0 check(hide='0' or hide='1')
);
create sequence seq_account_option;





