CREATE TABLE movies
(
    movie_id     INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    release_date DATE,
    rating       FLOAT,
    trailer_url  VARCHAR(255),
    image_id     int references images (image_id),
    is_active    boolean   DEFAULT TRUE,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    language     VARCHAR(50)
);

CREATE TABLE cinemas
(
    cinema_id         INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    location          VARCHAR(255),
    number_of_screens INT,
    created_at        timestamp DEFAULT CURRENT_TIMESTAMP,
    is_active         boolean   DEFAULT TRUE
);

CREATE TABLE screens
(
    screen_id        INT AUTO_INCREMENT PRIMARY KEY,
    cinema_id        int,
    screen_number    INT,
    seating_capacity INT,
    sound_system     VARCHAR(100),
    is_active        boolean   DEFAULT TRUE,
    created_at       timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cinema_id) REFERENCES Cinemas (cinema_id)
);

CREATE TABLE Showtimes
(
    showtime_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id    int,
    screen_id   int,
    start_time  TIMESTAMP,
    end_time    TIMESTAMP,
    language    VARCHAR(50),
    is_active   boolean   DEFAULT TRUE,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES Movies (movie_id),
    FOREIGN KEY (screen_id) REFERENCES Screens (screen_id)
);

CREATE TABLE users
(
    user_id           INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255),
    username          VARCHAR(255) UNIQUE,
    password          VARCHAR(255),
    phone_number      VARCHAR(20),
    role              VARCHAR(50),
    is_active         boolean   DEFAULT TRUE,
    image_id          int references images (image_id),
    created_at        timestamp DEFAULT CURRENT_TIMESTAMP,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE tickets
(
    ticket_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id       int,
    showtime_id   int,
    seat_number   VARCHAR(10),
    purchase_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price         DECIMAL(10, 2),
    is_active     boolean   DEFAULT TRUE,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (showtime_id) REFERENCES Showtimes (showtime_id)
);

CREATE TABLE reviews
(
    review_id  INT AUTO_INCREMENT PRIMARY KEY,
    user_id    int,
    movie_id   int,
    rating     FLOAT,
    comment    TEXT,
    is_active  boolean   DEFAULT TRUE,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (movie_id) REFERENCES Movies (movie_id)
);

CREATE TABLE transactions
(
    transaction_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id          int,
    total_amount     DECIMAL(10, 2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method   VARCHAR(50),
    is_active        boolean   DEFAULT TRUE,
    created_at       timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

create table images
(
    image_id       INT AUTO_INCREMENT PRIMARY KEY,
    name           varchar(100) not null,
    generated_name varchar(100) not null,
    mime_type      varchar(100) not null,
    extension      varchar(100),
    is_active      boolean   default true,
    created_at     timestamp default current_timestamp
);

create table category
(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name        varchar(100) not null,
    is_active   boolean   default true,
    created_at  timestamp default CURRENT_TIMESTAMP
);

create table movie_category
(
    move_category_id INT AUTO_INCREMENT PRIMARY KEY,
    move_id          int,
    category_id      int,
    foreign key (move_id) references movies (movie_id),
    foreign key (category_id) references category (category_id)
);

DELIMITER //

CREATE FUNCTION save_user(
    v_name VARCHAR(255),
    v_username VARCHAR(255),
    v_password VARCHAR(255),
    v_phone_number VARCHAR(255)
) RETURNS VARCHAR(100)
    DETERMINISTIC
BEGIN
    DECLARE new_id VARCHAR(100);

    IF EXISTS(SELECT * FROM users WHERE is_active = 1 AND username = v_username AND password = v_password) THEN
        RETURN NULL;
    END IF;

    INSERT INTO users(name, username, password, phone_number)
    VALUES (v_name, v_username, v_password, v_phone_number);

    SELECT user_id into new_id FROM users WHERE is_active = 1 AND username = v_username AND password = v_password;

    RETURN new_id;
END
//
DELIMITER ;

