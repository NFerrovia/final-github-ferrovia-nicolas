package com.example.movieservice.controller;

import com.example.movieservice.model.Series;
import com.example.movieservice.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping
public class SeriesController {

    private final SeriesService seriesService;
    @Autowired
    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Series> series = seriesService.findAll();
        return series.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(series);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id){
        Series series = seriesService.findById(id);
        return Objects.isNull(series)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(series);
    }

    @PostMapping
    public ResponseEntity<?> saveSeries(@RequestBody Series series){
        return ResponseEntity.ok(seriesService.saveSeries(series));
    }

}
