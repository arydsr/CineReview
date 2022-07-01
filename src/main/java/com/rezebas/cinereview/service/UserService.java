package com.rezebas.cinereview.service;

import com.rezebas.cinereview.controller.user.CreateModeratorResponse;
import com.rezebas.cinereview.domain.User;
import com.rezebas.cinereview.dto.UserLogin;
import com.rezebas.cinereview.enums.ProfileEnum;
import com.rezebas.cinereview.repositories.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(user.getPassword());
        user.setPassword(senhaEncoder);
        user.getProfile().setProfileName(ProfileEnum.ROLE_LEITOR);

        return Optional.of(userRepository.save(user));
    }

    public Optional<User> updateUser(User user) {

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String senhaEncoder = encoder.encode(user.getPassword());
            user.setPassword(senhaEncoder);

            return Optional.of(userRepository.save(user));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);

    }

    public CreateModeratorResponse createModerator(int id) {
        Optional<User> user = userRepository.findById((long) id);
        if (user.isPresent()) {
            user.get().getProfile().setProfileName(ProfileEnum.ROLE_MODERADOR);

            return new CreateModeratorResponse(user.get().getName(), user.get().getProfile().getProfileName().toString(),
                    "Moderador cadastrado com sucesso");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
    }

    public Optional<UserLogin> login(UserLogin userLogin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> usuario = userRepository.findByEmail(userLogin.getEmail());

        if (usuario.isPresent()) {

            if (encoder.matches(userLogin.getPassword(), usuario.get().getPassword())) {

                String auth = userLogin.getEmail() + ":" + userLogin.getPassword();
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
                String authHeader = "Basic " + new String(encodedAuth);

                userLogin.setEmail(usuario.get().getEmail());
                userLogin.setPassword(usuario.get().getPassword());
                userLogin.setToken(authHeader);
                return Optional.of(userLogin);
            }
        } throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");

    }
}
