INSERT INTO owner (username, password)
VALUES ('admin', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
-- здесь bcrypt-хэш пароля, например for "admin123" --
INSERT INTO owner_roles (owner_id, role)
SELECT id, 'ROLE_ADMIN'
FROM owner
WHERE username = 'admin';
