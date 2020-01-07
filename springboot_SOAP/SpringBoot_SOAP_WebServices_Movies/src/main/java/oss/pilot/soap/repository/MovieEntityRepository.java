package oss.pilot.soap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import oss.pilot.soap.entity.MovieEntity;

@Repository
public interface MovieEntityRepository extends CrudRepository<MovieEntity, Long> {

	public MovieEntity findByTitle(String title);
}
