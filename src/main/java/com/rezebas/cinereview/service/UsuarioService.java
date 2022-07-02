package com.rezebas.cinereview.service;

import com.rezebas.cinereview.controller.user.CreateModeratorResponse;
import com.rezebas.cinereview.domain.Usuario;
import com.rezebas.cinereview.domain.UsuarioLogin;
import com.rezebas.cinereview.enums.PerfilEnum;
import com.rezebas.cinereview.repositories.UsuarioRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    public Optional<Usuario> registerUser(Usuario user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(user.getSenha());
        user.setSenha(senhaEncoder);
        user.getPerfil().setProfileName(PerfilEnum.PERFIL_LEITOR);

        return Optional.of(userRepository.save(user));
    }

    public Optional<Usuario> updateUser(Usuario user) {

        Optional<Usuario> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String senhaEncoder = encoder.encode(user.getSenha());
            user.setSenha(senhaEncoder);

            return Optional.of(userRepository.save(user));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);

    }

    public CreateModeratorResponse createModerator(int id) {
        Optional<Usuario> user = userRepository.findById((long) id);
        if (user.isPresent()) {
            user.get().getPerfil().setProfileName(PerfilEnum.PERFIL_MODERADOR);

            return new CreateModeratorResponse(user.get().getNome(), user.get().getPerfil().getProfileName().toString(),
                    "Moderador cadastrado com sucesso");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
    }

    public Optional<UsuarioLogin> login(UsuarioLogin userLogin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = userRepository.findByEmail(userLogin.getEmail());

        if (usuario.isPresent()) {

            if (encoder.matches(userLogin.getSenha(), usuario.get().getSenha())) {

                String auth = userLogin.getEmail() + ":" + userLogin.getSenha();
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
                String authHeader = "Basic " + new String(encodedAuth);

                userLogin.setEmail(usuario.get().getEmail());
                userLogin.setSenha(usuario.get().getSenha());
                userLogin.setToken(authHeader);
                return Optional.of(userLogin);
            }
        } throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");

    }
}
