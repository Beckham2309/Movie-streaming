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

    // Find by path
    @GetMapping("/find-by-path/{path}")
    public ResponseEntity<List<MovieInfo>> findByPath(@PathVariable String path) {
        try {
            List<MovieInfo> movies = repository.findByPathContaining(path);
            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find by name
    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<List<MovieInfo>> findByName(@PathVariable String name) {
        try {
            List<MovieInfo> movies = repository.findByNameContainingIgnoreCase(name);
            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieInfo> getById(@PathVariable Long id) {
        try {
            MovieInfo movie = repository.findById(id).orElse(null);
            if (movie == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}