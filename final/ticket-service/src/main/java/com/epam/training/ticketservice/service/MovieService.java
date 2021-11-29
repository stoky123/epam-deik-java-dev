package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.repository.MovieRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

}
