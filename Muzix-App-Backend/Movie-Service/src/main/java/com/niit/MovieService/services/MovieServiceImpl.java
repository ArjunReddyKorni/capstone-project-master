package com.niit.MovieService.services;

import com.niit.MovieService.domain.Movie;
import com.niit.MovieService.exceptions.MovieAlreadyExistsException;
import com.niit.MovieService.exceptions.MovieNotFoundException;
import com.niit.MovieService.repository.MovieRepository;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public List<Movie> loadPopularMovies(List<Movie> movieList) throws IOException, InterruptedException {
//        String url = "https://api.themoviedb.org/3/tv/popular?api_key=15e383204c1b8a09dbfaaa4c01ed7e17&language=en-US&page=1";
//        HttpRequest  request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
//        HttpClient client = HttpClient.newBuilder().build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//        String result = response.body();

//        JsonObject obj = new JsonObject(result);
//        System.out.println(obj);
//        movieRepository.save(result.body())
//        JsonParser jsonParser = new JSONParser();
//        for(Movie movie : movieList){
//            movieRepository.insert(movie);
//        }
        for(int i = 0 ; i < movieList.size() ; i++){
            if(movieRepository.findById(movieList.get(i).getId()).isPresent()){
                Movie movie = movieRepository.findById(movieList.get(i).getId()).get();
                movie.getKeyWords().add("trending");
                movieRepository.save(movie);
            }else {
                movieList.get(i).setKeyWords(Arrays.asList("trending"));
                movieRepository.insert(movieList.get(i));
            }
        }
        return movieRepository.findAll();
    }
    @Override
    public List<Movie> loadFreeMovies(List<Movie> freeMovieList) {
        for(int i = 0 ; i < freeMovieList.size() ; i++){
            if(movieRepository.findById(freeMovieList.get(i).getId()).isPresent()){
                Movie movie = movieRepository.findById(freeMovieList.get(i).getId()).get();
                movie.getKeyWords().add("free");
                movieRepository.save(movie);
            }else {
                freeMovieList.get(i).setKeyWords(Arrays.asList("free"));
                movieRepository.insert(freeMovieList.get(i));
            }
        }

        return movieRepository.findAll();
    }

    @Override
    public List<Movie> loadTrendingMovies(List<Movie> trendingList) {
        for(int i = 0 ; i < trendingList.size() ; i++){
            if(movieRepository.findById(trendingList.get(i).getId()).isPresent()){
                 Movie movie = movieRepository.findById(trendingList.get(i).getId()).get();
                movie.getKeyWords().add("trending");
                movieRepository.save(movie);
            }else {
                trendingList.get(i).setKeyWords(Arrays.asList("trending"));
                movieRepository.insert(trendingList.get(i));
            }
        }
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> updateMovieList(List<Movie> movieList) {
        movieRepository.deleteAll();
       // for(int i = 0 ; i < movieList.size() ; i++){
            movieRepository.insert(movieList);
       // }
        return movieRepository.findAll();
    }

    @Override
    public boolean deleteAllMovies() {
        movieRepository.deleteAll();
        return true;
    }
    @Override
    public Movie addMovie(Movie movie) throws MovieAlreadyExistsException {
        if(movieRepository.findById(movie.getId()).isPresent()){
            throw new MovieAlreadyExistsException();
        }else {
            return movieRepository.insert(movie);
        }
    }

    @Override
    public boolean deleteMovie(int movieId) throws MovieNotFoundException {
        if(movieRepository.findById(movieId).isEmpty()){
            throw new MovieNotFoundException();
        }else {
            movieRepository.deleteById(movieId);
            return true;
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
