CREATE TABLE users (
    id SERIAL PRIMARY KEY NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    enabled boolean NOT NULL,
    credentials_non_expired boolean NOT NULL,
    account_non_locked boolean NOT NULL,
    account_non_expired boolean NOT NULL,
    updated_date timestamp NOT NULL,
    version integer NOT NULL
);

INSERT INTO USERS VALUES (1, 'admin','$2a$12$.HeqTVR6hs5gDvkdurRFZe18Roen7YwHeOGapMqaWhd9rl6aA3bW6','admin@example.com', true, true, true, true, TIMESTAMP '2024-01-31 00:00:00', 1);

CREATE TABLE account_type (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    logo VARCHAR(255) NOT NULL
);

INSERT INTO ACCOUNT_TYPE VALUES (1, 'Google', 'https://google.com', 'https://google.com/favicon.ico');
INSERT INTO ACCOUNT_TYPE VALUES (2, 'Facebook', 'https://facebook.com', 'https://facebook.com/favicon.ico');
INSERT INTO ACCOUNT_TYPE VALUES (3, 'Twitter', 'https://twitter.com', 'https://twitter.com/favicon.ico');
INSERT INTO ACCOUNT_TYPE VALUES (3, 'Twitter', 'https://twitter.com', 'https://twitter.com/favicon.ico');
INSERT INTO ACCOUNT_TYPE VALUES (4, 'Amazon', 'https://www.amazon.com', 'https://www.amazon.com/favicon.ico');
INSERT INTO ACCOUNT_TYPE VALUES (5, 'Netflix', 'https://www.netflix.com', 'https://www.netflix.com/favicon.ico');

