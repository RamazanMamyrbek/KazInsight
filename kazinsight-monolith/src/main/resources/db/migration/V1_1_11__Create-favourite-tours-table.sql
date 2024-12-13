CREATE TABLE favorite_tours (
                          tour_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          UNIQUE (user_id, tour_id)
);

ALTER TABLE favorite_tours
    ADD CONSTRAINT FK_favorite_tours_tour FOREIGN KEY (tour_id) REFERENCES tours,
    ADD CONSTRAINT FK_favorite_user FOREIGN KEY (user_id) REFERENCES users;