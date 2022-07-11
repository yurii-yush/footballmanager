CREATE TABLE teams
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(40)  NOT NULL,
    country_code CHAR(2) NOT NULL,
    balance BIGINT NOT NULL,
    commission REAL NOT NULL,
    is_active BOOLEAN
);

CREATE TABLE players
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(40)  NOT NULL,
    birth_date DATE NOT NULL,
    start_career_date DATE NOT NULL,
    team_id bigint,
    country_code CHAR(2) NOT NULL,
    position VARCHAR(20)  NOT NULL,
    is_active BOOLEAN
);

CREATE TABLE transfers
(
    id BIGSERIAL PRIMARY KEY,
    player_id bigint NOT NULL,
    from_team_id bigint,
    to_team_id bigint NOT NULL,
    price BIGINT NOT NULL,
    datetime TIMESTAMP WITH TIME ZONE  NOT NULL
);


