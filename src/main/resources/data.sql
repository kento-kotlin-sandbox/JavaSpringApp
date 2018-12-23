/* 従業員テーブルのデータ */
INSERT INTO employee(employee_id, employee_name, age)
VALUES(1, '山田太郎', 30);

/* ユーザーマスタテーブルのデータ(管理者権限あり) */
INSERT INTO m_user(user_id, password, user_name, birthday, age, marriage, role)
VALUES('kento75@xxx.co.jp', 'password', 'Kento75', '1993-07-05', 25, false, 'ROLE_ADMIN');

/* ユーザーマスタのデータ(一般権限) */
INSERT INTO m_user(user_id, password, user_name, birthday, age, marriage, role)
VALUES('kento@xxx.co.jp', 'password', 'Kento755', '1993-07-05', 25, false, 'ROLE_GENERAL');
