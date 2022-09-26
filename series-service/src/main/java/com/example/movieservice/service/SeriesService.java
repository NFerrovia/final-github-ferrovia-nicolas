package com.example.movieservice.service;

import com.example.movieservice.model.Series;
import com.example.movieservice.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    private static final Logger LOG = LoggerFactory.getLogger(SeriesService.class);

    @Value("${queue.series.name}")
    private String queueSeriesName;

    private final SeriesRepository seriesRepository;
    @Autowired
    public SeriesService(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public Series findById(String id){
        return seriesRepository.findById(id).orElse(null);
    }

    public List<Series> findAll(){
        return seriesRepository.findAll();
    }

    @RabbitListener(queues = "${queue.series.name}")
    public Series saveSeries(Series series){
        LOG.info("RabbitMQ: Guardando Serie: " + series.toString());
        return seriesRepository.save(series);
    }
}