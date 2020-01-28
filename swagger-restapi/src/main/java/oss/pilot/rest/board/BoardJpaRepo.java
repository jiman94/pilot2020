package oss.pilot.rest.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepo extends JpaRepository<Board, Long> {
    Board findByName(String name);
}
