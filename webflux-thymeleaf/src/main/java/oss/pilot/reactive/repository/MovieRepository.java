package oss.pilot.reactive.repository;

import oss.pilot.reactive.Movie;
import reactor.core.publisher.Flux;

public interface MovieRepository {

    Flux<Movie> findAll();

}
