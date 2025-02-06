CREATE TABLE IF NOT EXISTS post (
    id serial primary key,
    title varchar(255) not null,
    content text,
    tags varchar(50) array,
    image text,
    likes_count bigint default 0
);

CREATE TABLE IF NOT EXISTS comment (
    id serial primary key,
    post_id bigint,
    author varchar(50) not null,
    comment_content text not null,
    foreign key (post_id) references post (id)
);