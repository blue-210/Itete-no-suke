/* ユーザーマスタ */
CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(254) UNIQUE,
    birthday DATE,
    age SMALLINT,
    role VARCHAR(50) NOT NULL,
    status BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

/* 痛み記録マスタ */
CREATE TABLE IF NOT EXISTS notes (
    note_id BIGSERIAL PRIMARY KEY,
    pain_level VARCHAR(1) NOT NULL,
    medicine1 VARCHAR(50) NOT NULL,
    medicine2 VARCHAR(50) NOT NULL,
    medicine3 VARCHAR(50) NOT NULL,
    medicine4 VARCHAR(50) NOT NULL,
    medicine5 VARCHAR(50) NOT NULL,
    part1 VARCHAR(50) NOT NULL,
    part2 VARCHAR(50) NOT NULL,
    part3 VARCHAR(50) NOT NULL,
    part4 VARCHAR(50) NOT NULL,   
    part5 VARCHAR(50) NOT NULL,   
    part6 VARCHAR(50) NOT NULL,
    part7 VARCHAR(50) NOT NULL,
    part8 VARCHAR(50) NOT NULL,
    part9 VARCHAR(50) NOT NULL,   
    part10 VARCHAR(50) NOT NULL,
    memo VARCHAR(250) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users_notes (
    fk_user_id BIGSERIAL,
    fk_note_id BIGSERIAL,
    FOREIGN KEY (fk_user_id) REFERENCES users(user_id),
    FOREIGN KEY (fk_note_id) REFERENCES notes(note_id),
    PRIMARY KEY (fk_user_id, fk_note_id)
);