package com.rezebas.cinereview.controller.movie;

import com.rezebas.cinereview.clients.omdb.dto.OmdbMovieResponse;
import com.rezebas.cinereview.controller.movie.request.ReAvaliacaoRequest;
import com.rezebas.cinereview.controller.movie.request.PontosResquest;
import com.rezebas.cinereview.controller.movie.response.ReAvaliacaoResponse;
import com.rezebas.cinereview.controller.movie.response.RepeatedResponse;
import com.rezebas.cinereview.controller.movie.response.AvaliacaoResponse;
import com.rezebas.cinereview.controller.movie.request.AvaliacaoRequest;
import com.rezebas.cinereview.controller.movie.response.PontosResponse;
import com.rezebas.cinereview.repositories.FilmeRepository;
import com.rezebas.cinereview.service.FilmeService;
import com.rezebas.cinereview.controller.movie.response.ReacaoResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/filmes")
@AllArgsConstructor
public class MoviesController {

    @Autowired
    FilmeService movieService;

    @Autowired
    FilmeRepository movieRepository;


    @GetMapping("/titulo")
    public ResponseEntity<OmdbMovieResponse> getMovieByTitle(@RequestParam(name = "title", required = false) String title) {
        if ((Objects.isNull(title) || title.isBlank())){
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Necessario filtrar busca por titulo");
        }
        OmdbMovieResponse movie = movieService.getMovieByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OmdbMovieResponse> getMovieById(@PathVariable(name = "id") String id) {
        if (Objects.isNull(id)){
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Necessario filtrar busca por id");
        }
        OmdbMovieResponse movie = movieService.getMovieById(id);
        return ResponseEntity.status(HttpStatus.OK).body(movie);
    }

    @PostMapping("/avaliacao")
    public ResponseEntity<PontosResponse> postScoreMovie(@Valid @RequestBody PontosResquest scoreResquest){

        PontosResponse scoreResponse = movieService.postScoreMovie(scoreResquest);
        return ResponseEntity.status(201).body(scoreResponse);
    }

    @PostMapping("/comentario")
    public ResponseEntity<AvaliacaoResponse> postReviewMovie(@Valid @RequestBody AvaliacaoRequest reviewResquest){
            AvaliacaoResponse reviewResponse = movieService.postReviewMovie(reviewResquest);
            return ResponseEntity.status(201).body(reviewResponse);
    }

    @PostMapping("/comentario/resposta/{id}")
    public ResponseEntity<ReAvaliacaoResponse> postAnswerReviewMovie(@PathVariable int id,
                                                                     @Valid @RequestBody ReAvaliacaoRequest answerReviewRequest){
        ReAvaliacaoResponse answerReviewResponse = movieService.postAnswerReviewMovie(id, answerReviewRequest);
        return ResponseEntity.status(201).body(answerReviewResponse);
    }

    @PatchMapping("/comentario/repetido/{id}")
    public ResponseEntity<RepeatedResponse> putRepeatedReview(@PathVariable int id,
                                                              @RequestParam(name = "comentario-repetido", required = true) boolean repeated) {

        RepeatedResponse repeatedResponse = movieService.putRepeatedReview(id, repeated);
        return ResponseEntity.status(201).body(repeatedResponse);
    }

    @PatchMapping("/comentario/like/{id}")
    public ResponseEntity<ReacaoResponse> putLikeComment(@PathVariable int id,
                                                         @RequestParam(name = "like", required = true) boolean like) {
        ReacaoResponse likeResponse = movieService.likeReview(id, like);
        return ResponseEntity.status(201).body(likeResponse);
    }

    @PatchMapping("/comentario/dislike/{id}")
    public ResponseEntity<ReacaoResponse> putDislikeComment(@PathVariable int id,
                                                            @RequestParam(name = "dislike", required = true) boolean dislike){
        ReacaoResponse dislikeResponse = movieService.dislikeReview(id, dislike);
        return ResponseEntity.status(201).body(dislikeResponse);
    }
}
