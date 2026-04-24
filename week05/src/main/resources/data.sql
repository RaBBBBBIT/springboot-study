DELETE FROM tb_user;

INSERT INTO tb_user (id, username, password, age, email, create_time)
VALUES (1, 'rabbit', '123456', 24, 'rabbit@example.com', NOW());
