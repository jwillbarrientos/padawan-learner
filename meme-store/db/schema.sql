-- ddl data definition language meme store

drop table if exists client cascade;
-- users table
create table client (
    id bigint primary key,
    email varchar not null unique,
    password varchar not null
);

drop table if exists video cascade;
-- videos table
create table video (
    id bigint primary key,
    name varchar not null,
    link varchar,
    path varchar,
    duration_seconds int,
    file_size int,
    video_state varchar,
    date timestamp,
    client_id bigint not null,
    foreign key (client_id) references client(id) on delete cascade
);

drop table if exists tag cascade;
-- tags table
create table tag (
    id bigint primary key,
    name varchar not null,
    client_id bigint not null,
    foreign key (client_id) references client(id) on delete cascade
);

drop table if exists video_tag cascade;
-- video_tag table (many-to-many relationship)
create table video_tag (
    video_id bigint not null,
    tag_id bigint not null,
    primary key (video_id, tag_id),
    foreign key (video_id) references video(id) on delete cascade,
    foreign key (tag_id) references tag(id) on delete cascade
);
