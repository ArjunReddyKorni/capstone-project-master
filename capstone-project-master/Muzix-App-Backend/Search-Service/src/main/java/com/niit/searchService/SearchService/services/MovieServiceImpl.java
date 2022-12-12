package com.niit.searchService.SearchService.services;

import com.niit.searchService.SearchService.domain.Movie;
import com.niit.searchService.SearchService.exception.MovieNotFoundException;
import com.niit.searchService.SearchService.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie searchMovieByName(String name) throws MovieNotFoundException {
        return movieRepository.findByName(name);
    }
}
