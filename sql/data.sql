/* ユーザーマスタのデータ（一般権限） */
INSERT INTO users (
    password,
    user_name,
    email,
    birthday,
    age,
    role,
    status,
    updated_at,
    created_at)
VALUES(
    '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa',
    'テスト太郎',
    'test1234@gmail.com',
    '1988-06-04',
     31,
     'ROLE_GENERAL',
     true,
     NOW(),
     NOW()
 );
