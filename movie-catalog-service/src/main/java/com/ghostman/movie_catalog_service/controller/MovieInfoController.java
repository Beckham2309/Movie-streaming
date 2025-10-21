package com.ghostman.movie_catalog_service.controller;

import com.ghostman.movie_catalog_service.model.MovieInfo;
import com.ghostman.movie_catalog_service.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {

    @Autowired
    private MovieRepo repository;

    @PostMapping("/save")
    public ResponseEntity<List<MovieInfo>> saveAllMovies(@RequestBody List<MovieInfo> movieInfoList) {
        try {
            List<MovieInfo> savedMovies = repository.saveAll(movieInfoList);
            return new ResponseEntity<>(savedMovies, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MovieInfo>> getAllMovies() {
        try {
            List<MovieInfo> movies = repository.findAll();
            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}