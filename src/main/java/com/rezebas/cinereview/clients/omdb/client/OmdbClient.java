package com.rezebas.cinereview.clients.omdb.client;

import com.rezebas.cinereview.clients.omdb.dto.OmdbMovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "omdbClient", url = "${omdb.url}")
public interface OmdbClient {

    @GetMapping
    OmdbMovieResponse getMovie(@RequestParam("apikey") String apiKey,
                               @RequestParam(name = "t", required = false) String title,
                               @RequestParam(name = "i", required = false) String id);
}


