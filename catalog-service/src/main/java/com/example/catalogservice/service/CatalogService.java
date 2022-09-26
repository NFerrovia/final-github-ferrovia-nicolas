package com.example.catalogservice.service;

import com.example.catalogservice.model.CatalogDTO;
import com.example.catalogservice.model.MovieDTO;
import com.example.catalogservice.model.SerieDTO;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    private MovieFeignClient movieFeignClient;
    private final RabbitTemplate rabbitTemplate;

    private final Logger LOG = LoggerFactory.getLogger(CatalogService.class);

    @Autowired
    public CatalogService(MovieFeignClient movieFeignClient, RabbitTemplate rabbitTemplate) {
        this.movieFeignClient = movieFeignClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public CatalogDTO findCatalogByGenre(String genre) {
        ResponseEntity<List<MovieDTO>> movieResponse = movieFeignClient.findMovieByGenre(genre);
        if (movieResponse.getStatusCode().is2xxSuccessful())
            return new CatalogDTO(LocalDate.now().toString(), movieResponse.getBody());
        return null;
    }

    @Value("${queue.movie.name}")
    private String queueName;

    @Value("${queue.series.name}")
    private String queueSeriesName;

    public ResponseEntity<List<MovieDTO>> findMovieByGenre(String genre){
        LOG.info("Buscando por genero:" + genre);
        return movieFeignClient.getMovieByGenre(genre);
    }

    @CircuitBreaker(name = "movies", fallbackMethod = "moviesFallBackMethod")
    public ResponseEntity<List<MovieDTO>> findMovieByGenre(String genre, Boolean throwError){
        LOG.info("Buscando por genero:" + genre);
        return movieFeignClient.getMovieByGenreWithThrowError(genre, throwError);
    }

    public ResponseEntity<List<MovieDTO>> moviesFallBackMethod(CallNotPermittedException exception){
        LOG.info("Error al buscar peliculas, Circuit Breaker: On");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    public void saveMovie(MovieDTO movieDTO){
        rabbitTemplate.convertAndSend(queueName, movieDTO);
    }

    public void saveSeries(SerieDTO serieDTO){
        rabbitTemplate.convertAndSend(queueSeriesName, serieDTO);
    }

}
