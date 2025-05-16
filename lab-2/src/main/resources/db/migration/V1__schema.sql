CREATE TABLE owner (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
    -- остальные поля из Owner.java --
);
CREATE TABLE owner_roles (
                             owner_id BIGINT NOT NULL,
                             role VARCHAR(20) NOT NULL,
                             CONSTRAINT fk_owner FOREIGN KEY(owner_id) REFERENCES owner(id)
);
-- таблицы pets и связи, как было в ЛР-3 --
