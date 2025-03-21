CREATE TABLE owners (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        birth_date VARCHAR(255)
);

CREATE TABLE pets (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255),
                      birth_date VARCHAR(255),
                      breed VARCHAR(255),
                      color VARCHAR(255),
                      owner_id INTEGER REFERENCES owners(id)
);

CREATE TABLE pet_friends (
                             pet_id INTEGER REFERENCES pets(id),
                             friend_id INTEGER REFERENCES pets(id),
                             PRIMARY KEY (pet_id, friend_id)
);
