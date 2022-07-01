package com.rezebas.cinereview.clients.omdb.impl;

import com.rezebas.cinereview.clients.omdb.client.OmdbClient;
import com.rezebas.cinereview.clients.omdb.dto.OmdbMovieResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OmdbClientService {

    @Value("${apiKey}")
    private String apiKey;

    private final OmdbClient omdbClient;

    public OmdbMovieResponse searchMovieByTitle(String title) {
        return omdbClient.getMovie(apiKey, title, null);
    }

    public OmdbMovieResponse searchMovieById(String id) {
        return omdbClient.getMovie(apiKey, null, id);
    }
}
