package com.example.catalogservice.controller;

import com.example.catalogservice.model.CatalogDTO;
import com.example.catalogservice.model.MovieDTO;
import com.example.catalogservice.model.SerieDTO;
import com.example.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/catalog/")
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{genre}")
    public ResponseEntity<?> findCatalogByGenre(@PathVariable("genre") String genre) {
        CatalogDTO catalogDTO = catalogService.findCatalogByGenre(genre);
        return Objects.isNull(catalogDTO)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(catalogDTO, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMovie(@RequestBody MovieDTO movieDTO){
        catalogService.saveMovie(movieDTO);
        return ResponseEntity.ok("Movie was sent to queue");
    }

    @PostMapping("/save/series")
    public ResponseEntity<String> saveSeries(@RequestBody SerieDTO serieDTO){
        catalogService.saveSeries(serieDTO);
        return ResponseEntity.ok("Series was sent to queue");
    }

}
