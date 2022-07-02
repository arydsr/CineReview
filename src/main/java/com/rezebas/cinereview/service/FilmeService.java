package com.rezebas.cinereview.service;

import com.rezebas.cinereview.clients.omdb.dto.OmdbMovieResponse;
import com.rezebas.cinereview.clients.omdb.impl.OmdbClientService;
import com.rezebas.cinereview.controller.movie.request.ReAvaliacaoRequest;
import com.rezebas.cinereview.controller.movie.response.ReAvaliacaoResponse;
import com.rezebas.cinereview.controller.movie.response.RepeatedResponse;
import com.rezebas.cinereview.controller.movie.request.AvaliacaoRequest;
import com.rezebas.cinereview.controller.movie.request.PontosResquest;
import com.rezebas.cinereview.controller.movie.response.AvaliacaoResponse;
import com.rezebas.cinereview.controller.movie.response.PontosResponse;
import com.rezebas.cinereview.domain.*;
import com.rezebas.cinereview.enums.PerfilEnum;
import com.rezebas.cinereview.repositories.FilmeRepository;
import com.rezebas.cinereview.repositories.AvaliacaoRepository;
import com.rezebas.cinereview.repositories.PontosRepository;
import com.rezebas.cinereview.repositories.UsuarioRepository;
import com.rezebas.cinereview.controller.movie.response.ReacaoResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmeService {

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    FilmeRepository movieRepository;

    @Autowired
    PontosRepository scoreRepository;

    @Autowired
    AvaliacaoRepository reviewRepository;

    @Autowired
    OmdbClientService omdbClientService;

    public OmdbMovieResponse getMovieByTitle(String title) {
        return omdbClientService.searchMovieByTitle(title);
    }

    public OmdbMovieResponse getMovieById(String id) {
        return omdbClientService.searchMovieById(id);
    }

    public PontosResponse postScoreMovie(PontosResquest scoreResquest) {

        Optional<Usuario> user = userRepository.findByEmail(scoreResquest.getEmail());

        getMovieById(scoreResquest.getFilmeId());

        if (user.isPresent()) {
            scoreRepository.save(Pontos.builder()
                    .movie(scoreResquest.getTitulo())
                    .userClient(user.get())
                    .build());

            addPointsToUser(user.get());
            upgradeProfile(user.get());

            return new PontosResponse(scoreResquest.getTitulo(),
                    scoreResquest.getFilmeId(),
                    "Nota atribuída com sucesso");
        }
        throw new RuntimeException("Nao foi possível atribuir a nota");
    }

    public AvaliacaoResponse postReviewMovie(AvaliacaoRequest reviewResquest) {

        Optional<Usuario> user = userRepository.findByEmail(reviewResquest.getEmail());

        if (user.isPresent()) {
            reviewRepository.save(Avaliacao.builder()
                    .text(reviewResquest.getAvaliacaoTexto())
                    .username(user.get().getName())
                    .build());

            user.get().setPoints(Integer.sum((user.get().getPoints()),1));
            userRepository.save(user.get());

            return new AvaliacaoResponse(reviewResquest.getTitulo(), reviewResquest.getFilmeId(), "Comentário adicionado com sucesso");
        }
        throw new RuntimeException("Não foi possível adicionar comentário");
    }

    public RepeatedResponse putRepeatedReview(int id, boolean repeated) {

        Optional<Avaliacao> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            review.get().setRepeated(repeated);
            reviewRepository.save(review.get());

            return new RepeatedResponse("Comentário marcado como repetido");
        }
        throw new RuntimeException("Nao foi possível localizar o comentário");
    }

    public ReacaoResponse dislikeReview(int id, boolean like) {
        Optional<Avaliacao> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            review.get().setDislike(review.get().getDislike());
            reviewRepository.save(review.get());

            return new ReacaoResponse("Dislike adicionado com sucesso");
        }
        throw new RuntimeException("Não foi possível realizar a operação dislike");
    }

    public ReacaoResponse likeReview(int id, boolean like) {

        Optional<Avaliacao> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            review.get().setDislike(review.get().getDislike());
            reviewRepository.save(review.get());

            return new ReacaoResponse("Like adicionado com sucesso");
        }
        throw new RuntimeException("Não foi possível realizar a operação like");
    }

    public ReAvaliacaoResponse postAnswerReviewMovie(int id, ReAvaliacaoRequest answerReviewRequest) {
        Optional<Avaliacao> review = reviewRepository.findById(id);
        Optional<Usuario> user = userRepository.findByEmail(answerReviewRequest.getEmail());

        if (review.isPresent() && user.isPresent()) {
            review.get().setAnswer(List.of(new ReAvaliacao(answerReviewRequest.getEmail(), answerReviewRequest.getAvaliacaoTexto())));
            reviewRepository.save(review.get());

            addPointsToUser(user.get());
            upgradeProfile(user.get());

            return new ReAvaliacaoResponse("Comentario adicionado com sucesso");
        }
        throw new RuntimeException("Nao foi possivel adicionar o comentario");
    }

    private void addPointsToUser(Usuario user){
        user.setPoints(Integer.sum((user.getPoints()),1));
    }

    private void upgradeProfile(Usuario user){
        if (user.getPoints() >= 1000) {
            user.getProfile().setProfileName(PerfilEnum.ROLE_MODERADOR);
        }
        else if (user.getPoints() >= 100) {
            user.getProfile().setProfileName(PerfilEnum.ROLE_AVANCADO);
        }
        else if (user.getPoints() >= 20)
            user.getProfile().setProfileName(PerfilEnum.ROLE_BASICO);
        else
            user.getProfile().setProfileName(PerfilEnum.ROLE_LEITOR);
    }

}