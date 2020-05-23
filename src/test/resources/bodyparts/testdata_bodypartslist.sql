DELETE FROM notes_bodyparts;
DELETE FROM users_bodyparts;
DELETE FROM bodyparts;

INSERT INTO bodyparts (body_parts_name, created_at, updated_at) VALUES ('部位登録テスト1', now(), now());
INSERT INTO bodyparts (body_parts_name, created_at, updated_at) VALUES ('部位登録テスト2', now(), now());
INSERT INTO users_bodyparts SELECT '1', body_parts_id FROM bodyparts;