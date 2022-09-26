package com.example.catalogservice.model;

import java.util.List;

public class CatalogDTO {

    private String creationDate;
    private List<MovieDTO> movies;

    public CatalogDTO() {
        //No-args constructor
    }

    public CatalogDTO(String creationDate, List<MovieDTO> movies) {
        this.creationDate = creationDate;
        this.movies = movies;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "CatalogDTO{" +
                "creationDate='" + creationDate + '\'' +
                ", movies=" + movies +
                '}';
    }
}
