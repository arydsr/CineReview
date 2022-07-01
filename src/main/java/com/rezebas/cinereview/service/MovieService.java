package com.rezebas.cinereview.service;

import com.rezebas.cinereview.clients.omdb.dto.OmdbMovieResponse;
import com.rezebas.cinereview.clients.omdb.impl.OmdbClientService;
import com.rezebas.cinereview.controller.movie.request.AnswerReviewRequest;
import com.rezebas.cinereview.controller.movie.response.AnswerReviewResponse;
import com.rezebas.cinereview.controller.movie.response.RepeatedResponse;
import com.rezebas.cinereview.controller.movie.request.ReviewRequest;
import com.rezebas.cinereview.controller.movie.request.ScoreResquest;
import com.rezebas.cinereview.controller.movie.response.ReviewResponse;
import com.rezebas.cinereview.controller.movie.response.ScoreResponse;
import com.rezebas.cinereview.domain.*;
import com.rezebas.cinereview.enums.ProfileEnum;
import com.rezebas.cinereview.repositories.MovieRepository;
import com.rezebas.cinereview.repositories.ReviewRepository;
import com.rezebas.cinereview.repositories.ScoreRepository;
import com.rezebas.cinereview.repositories.UserRepository;
import com.rezebas.cinereview.controller.movie.response.ReactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    OmdbClientService omdbClientService;

    public OmdbMovieResponse getMovieByTitle(String title) {
        return omdbClientService.searchMovieByTitle(title);
    }

    public OmdbMovieResponse getMovieById(String id) {
        return omdbClientService.searchMovieById(id);
    }

    public ScoreResponse postScoreMovie(ScoreResquest scoreResquest) {

        Optional<User> user = userRepository.findByEmail(scoreResquest.getEmail());

        getMovieById(scoreResquest.getMovieId());

        if (user.isPresent()) {
            scoreRepository.save(Score.builder()
                    .movie(scoreResquest.getTitle())
                    .userClient(user.get())
                    .build());

            addPointsToUser(user.get());
            upgradeProfile(user.get());

            return new ScoreResponse(scoreResquest.getTitle(),
                    scoreResquest.getMovieId(),
                    "Nota atribuída com sucesso");
        }
        throw new RuntimeException("Nao foi possível atribuir a nota");
    }

    public ReviewResponse postReviewMovie(ReviewRequest reviewResquest) {

        Optional<User> user = userRepository.findByEmail(reviewResquest.getEmail());

        if (user.isPresent()) {
            reviewRepository.save(Review.builder()
                    .text(reviewResquest.getReviewText())
                    .username(user.get().getName())
                    .build());

            user.get().setPoints(Integer.sum((user.get().getPoints()),1));
            userRepository.save(user.get());

            return new ReviewResponse(reviewResquest.getTitle(), reviewResquest.getMovieId(), "Comentário adicionado com sucesso");
        }
        throw new RuntimeException("Não foi possível adicionar comentário");
    }

    public RepeatedResponse putRepeatedReview(int id, boolean repeated) {

        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            review.get().setRepeated(repeated);
            reviewRepository.save(review.get());

            return new RepeatedResponse("Comentário marcado como repetido");
        }
        throw new RuntimeException("Nao foi possível localizar o comentário");
    }

    public ReactionResponse dislikeReview(int id, boolean like) {
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            review.get().setDislike(review.get().getDislike());
            reviewRepository.save(review.get());

            return new ReactionResponse("Dislike adicionado com sucesso");
        }
        throw new RuntimeException("Não foi possível realizar a operação dislike");
    }

    public ReactionResponse likeReview(int id, boolean like) {

        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            review.get().setDislike(review.get().getDislike());
            reviewRepository.save(review.get());

            return new ReactionResponse("Like adicionado com sucesso");
        }
        throw new RuntimeException("Não foi possível realizar a operação like");
    }

    public AnswerReviewResponse postAnswerReviewMovie(int id, AnswerReviewRequest answerReviewRequest) {
        Optional<Review> review = reviewRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(answerReviewRequest.getEmail());

        if (review.isPresent() && user.isPresent()) {
            review.get().setAnswer(List.of(new Reply(answerReviewRequest.getEmail(), answerReviewRequest.getAnswerText())));
            reviewRepository.save(review.get());

            addPointsToUser(user.get());
            upgradeProfile(user.get());

            return new AnswerReviewResponse("Comentario adicionado com sucesso");
        }
        throw new RuntimeException("Nao foi possivel adicionar o comentario");
    }

    private void addPointsToUser(User user){
        user.setPoints(Integer.sum((user.getPoints()),1));
    }

    private void upgradeProfile(User user){
        if (user.getPoints() >= 1000) {
            user.getProfile().setProfileName(ProfileEnum.ROLE_MODERADOR);
        }
        else if (user.getPoints() >= 100) {
            user.getProfile().setProfileName(ProfileEnum.ROLE_AVANCADO);
        }
        else if (user.getPoints() >= 20)
            user.getProfile().setProfileName(ProfileEnum.ROLE_BASICO);
        else
            user.getProfile().setProfileName(ProfileEnum.ROLE_LEITOR);
    }

}