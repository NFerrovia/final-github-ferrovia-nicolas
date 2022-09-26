package com.example.gatewayservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @CircuitBreaker(name= "moviesService")
    @GetMapping("/movies")
    public ResponseEntity<String> moviesFallBack(){
        return new ResponseEntity<>("Movies Service unresponsive",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CircuitBreaker(name= "catalogService")
    @GetMapping("/catalogs")
    public ResponseEntity<String> catalogsFallBack(){
        return new ResponseEntity<>("Catalogs Service unresponsive",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CircuitBreaker(name= "seriesService")
    @GetMapping("/series")
    public ResponseEntity<String> seriesFallBack(){
        return new ResponseEntity<>("Series services unresponsive",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
