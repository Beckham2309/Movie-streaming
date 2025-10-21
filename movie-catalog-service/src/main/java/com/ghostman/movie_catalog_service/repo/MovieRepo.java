package com.ghostman.movie_catalog_service.repo;

import com.ghostman.movie_catalog_service.model.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<MovieInfo, Long> {

}