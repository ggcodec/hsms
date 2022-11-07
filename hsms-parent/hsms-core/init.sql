drop database hsms;
create database hsms character set utf8mb4;

use hsms;

show tables ;

-- 用户信息表
create table users
(
    id       bigint primary key auto_increment,
    name     varchar(255),
    username varchar(255),
    password varchar(255),
    age      int,
    unique (username),
    index (password)
);

-- 房源价格和租赁状态表
create table price_state
(
    id    bigint primary key auto_increment,
    price double,
    state char, -- 0/1 是否租赁
        index (price, state)
);

-- 发布者信息表
create table publishers
(
    id          bigint primary key auto_increment,
    name        varchar(255), -- 发布者姓名
        age         int,          -- 发布者年龄
        house_count int,          -- 已发布房源数
        index (name)
);

-- 员工信息表
create table staffs
(
    id    bigint primary key auto_increment,
    name  varchar(255), -- 中介员工姓名
        staff_rank  varchar(255), -- 中介员工职级
        state char          -- 0/1 是否在职
);

-- 房源信息表
create table houses
(
    id             bigint primary key auto_increment,
    address        varchar(255), -- 房屋地址
        publisher_id   bigint,       -- 发布者id
        price_state_id bigint,       -- 价格和状态关联id
        staff_id       bigint,       -- 员工信息关联id
        foreign key (publisher_id) references publishers (id),
    foreign key (price_state_id) references price_state (id),
    foreign key (staff_id) references staffs (id)
);

-- 房屋图片信息表
create table images
(
    id       bigint primary key auto_increment,
    url      varchar(255),
    house_id bigint,
    foreign key (house_id) references houses (id)
);

-- 系统管理员信息表
create table manages
(
    id       bigint primary key auto_increment,
    name     varchar(255), -- 账号所属人名称
        username varchar(255), -- 管理员登录账号
        password varchar(255), -- 管理员登录密码
        state    char          -- 0/1 是否在职
);