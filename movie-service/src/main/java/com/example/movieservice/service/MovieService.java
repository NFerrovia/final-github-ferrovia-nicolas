package com.example.movieservice.service;


import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findByGenre(String genre, Boolean throwError) {
        LOG.info("Inicia busqueda de peliculas por género" + genre);
        if (throwError){
            LOG.error("Error en la busqueda de genero: " + genre);
            throw new RuntimeException("Error");
        }
        return this.movieRepository.findByGenre(genre);
    }

    @RabbitListener(queues = "${queue.movie.name}")
    public Movie saveMovie(Movie movie){
        LOG.info("RabbitMQ: Guardando pelicula " + movie.toString());
        return movieRepository.save(movie);
    }

}
