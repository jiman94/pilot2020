

CREATE TABLE movies (
  movie_id BIGINT(5) NOT NULL AUTO_INCREMENT,
  title VARCHAR(45) NULL,
  category VARCHAR(45) NULL,
  PRIMARY KEY (movie_id));
  
  select * from movies;
  
  INSERT INTO movies(movie_id, title, category) VALUES
  (101, 'Mama mia', 'drama'),
  (102, 'Mission Impossible', 'action'),
  (103, 'Coco', 'animation');