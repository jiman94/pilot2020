package oss.pilot.rest.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post, Long> {
    List<Post> findByBoardOrderByPostIdDesc(Board board);
}