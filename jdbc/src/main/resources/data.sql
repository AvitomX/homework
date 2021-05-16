-- Database: db_jdbc

-- DROP DATABASE db_jdbc;

CREATE DATABASE db_jdbc
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.usr

-- DROP TABLE public.usr;

CREATE TABLE public.usr
(
    id bigint NOT NULL DEFAULT nextval('usr_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    age integer,
    CONSTRAINT usr_pk PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.usr
    OWNER to postgres;
COMMENT ON TABLE public.usr
    IS 'Пользователи';
-- Index: usr_id_uindex

-- DROP INDEX public.usr_id_uindex;

CREATE UNIQUE INDEX usr_id_uindex
    ON public.usr USING btree
        (id ASC NULLS LAST)
    TABLESPACE pg_default;

-- Table: public.messages

-- DROP TABLE public.messages;

CREATE TABLE public.messages
(
    id bigint NOT NULL DEFAULT nextval('messages_id_seq'::regclass),
    text text COLLATE pg_catalog."default" NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT messages_pk PRIMARY KEY (id),
    CONSTRAINT messages_usr_fk FOREIGN KEY (author_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.messages
    OWNER to postgres;
COMMENT ON TABLE public.messages
    IS 'Сообщения';
-- Index: messages_id_uindex

-- DROP INDEX public.messages_id_uindex;

CREATE UNIQUE INDEX messages_id_uindex
    ON public.messages USING btree
        (id ASC NULLS LAST)
    TABLESPACE pg_default;

-- Table: public.comments

-- DROP TABLE public.comments;

CREATE TABLE public.comments
(
    id bigint NOT NULL DEFAULT nextval('comments_id_seq'::regclass),
    text text COLLATE pg_catalog."default" NOT NULL,
    message_id bigint NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT comments_pk PRIMARY KEY (id),
    CONSTRAINT comments_messages_id_fk FOREIGN KEY (message_id)
        REFERENCES public.messages (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT comments_usr_fk FOREIGN KEY (author_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.comments
    OWNER to postgres;
COMMENT ON TABLE public.comments
    IS 'Комментарии';
-- Index: comments_id_uindex

-- DROP INDEX public.comments_id_uindex;

CREATE UNIQUE INDEX comments_id_uindex
    ON public.comments USING btree
        (id ASC NULLS LAST)
    TABLESPACE pg_default;