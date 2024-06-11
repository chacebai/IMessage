-- Translated SQL for SQLite with unique index names
create table contact
(
    id          integer primary key autoincrement,
    uid         integer not null,
    room_id     integer not null,
    read_time   timestamp default CURRENT_TIMESTAMP not null,
    active_time timestamp,
    last_msg_id integer,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null,
    unique (uid, room_id)
);

create index idx_contact_create_time
    on contact (create_time);

create index idx_contact_room_id_read_time
    on contact (room_id, read_time);

create index idx_contact_update_time
    on contact (update_time);

create index idx_contact_user_id_active_time
    on contact (uid, active_time);

create table group_member
(
    id          integer primary key autoincrement,
    group_id    integer not null,
    uid         integer not null,
    role        integer not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null
);

create index idx_group_member_create_time
    on group_member (create_time);

create index idx_group_member_group_id_role
    on group_member (group_id, role);

create index idx_group_member_update_time
    on group_member (update_time);

-- 修改后的SQLite创建语句
create table message
(
    id           integer primary key autoincrement,
    room_id      integer not null,
    from_uid     integer not null,
    content      varchar(1024),
    reply_msg_id integer,
    status       integer not null,
    gap_count    integer,
    type         integer default 1,
    extra        json,
    create_time  timestamp default CURRENT_TIMESTAMP not null,
    update_time  timestamp default CURRENT_TIMESTAMP not null
);

create index idx_message_create_time
    on message (create_time);

create index idx_message_from_uid
    on message (from_uid);

create index idx_message_room_id
    on message (room_id);

create index idx_message_update_time
    on message (update_time);


-- 修改后的SQLite创建语句
create table room
(
    id          integer primary key autoincrement,
    type        integer not null,
    hot_flag    integer default 0,
    active_time timestamp default CURRENT_TIMESTAMP not null,
    last_msg_id integer,
    ext_json    json,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null
);

create index idx_room_create_time
    on room (create_time);

create index idx_room_update_time
    on room (update_time);

-- 修改后的SQLite创建语句
create table room_friend
(
    id          integer primary key autoincrement,
    room_id     integer not null,
    uid1        integer not null,
    uid2        integer not null,
    room_key    varchar(64) not null,
    status      integer not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null,
    unique (room_key)
);

create index idx_room_friend_create_time
    on room_friend (create_time);

create index idx_room_friend_room_id
    on room_friend (room_id);

create index idx_room_friend_update_time
    on room_friend (update_time);


-- 修改后的SQLite创建语句
create table room_group
(
    id            integer primary key autoincrement,
    room_id       integer not null,
    name          varchar(16) not null,
    avatar        varchar(256) not null,
    ext_json      json,
    delete_status integer default 0 not null,
    create_time   timestamp default CURRENT_TIMESTAMP not null,
    update_time   timestamp default CURRENT_TIMESTAMP not null
);

create index idx_room_group_create_time
    on room_group (create_time);

create index idx_room_group_room_id
    on room_group (room_id);

create index idx_room_group_update_time
    on room_group (update_time);



-- 修改后的SQLite创建语句
create table user_friend
(
    id            integer primary key autoincrement,
    uid           integer not null,
    friend_uid    integer not null,
    delete_status integer default 0 not null,
    create_time   timestamp default CURRENT_TIMESTAMP not null,
    update_time   timestamp default CURRENT_TIMESTAMP not null,
    unique (uid, friend_uid)
);

create index idx_user_friend_create_time
    on user_friend (create_time);

create index idx_user_friend_uid_friend_uid
    on user_friend (uid, friend_uid);

create index idx_user_friend_update_time
    on user_friend (update_time);


-- 修改后的SQLite创建语句
create table user_apply
(
    id          integer primary key autoincrement,
    uid         integer not null,
    type        integer not null,
    target_id   integer not null,
    msg         varchar(64) not null,
    status      integer not null,
    read_status integer not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null,
    unique (uid, target_id)
);

create index idx_user_apply_create_time
    on user_apply (create_time);

create index idx_user_apply_target_id
    on user_apply (target_id);

create index idx_user_apply_target_id_read_status
    on user_apply (target_id, read_status);

create index idx_user_apply_uid_target_id
    on user_apply (uid, target_id);

create index idx_user_apply_update_time
    on user_apply (update_time);

-- 创建User表
CREATE TABLE user (
    id INTEGER PRIMARY KEY AUTOINCREMENT, -- 用户id
    password TEXT NOT NULL,               -- 用户密码
    name TEXT NOT NULL,                   -- 用户昵称
    avatar TEXT,                          -- 用户头像
    sex INTEGER,                          -- 性别 1为男性，2为女性
    open_id TEXT,                         -- 微信openid用户标识
    active_status INTEGER DEFAULT 2,      -- 在线状态 1在线 2离线
    last_opt_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 最后上下线时间
    ip_info TEXT,                         -- ip信息
    item_id INTEGER,                      -- 佩戴的徽章id
    status INTEGER,                       -- 使用状态 0.正常 1拉黑
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP  -- 修改时间
);

-- 在SQLite中没有直接支持的注释语法，但是可以用注释符号添加注释
-- 用户表












