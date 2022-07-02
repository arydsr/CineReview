package com.rezebas.cinereview.controller.user;

import com.rezebas.cinereview.domain.Usuario;
import com.rezebas.cinereview.domain.UsuarioLogin;
import com.rezebas.cinereview.repositories.UsuarioRepository;
import com.rezebas.cinereview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private UsuarioService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Optional<Usuario>> postUsuario(@RequestBody Usuario user) {
        Optional<Usuario> novoUsuario = userService.registerUser(user);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticationUsuario(@RequestBody UsuarioLogin userLogin) {
        try {
            Optional<UsuarioLogin> user = userService.login(userLogin);
            return ResponseEntity.status(HttpStatus.CREATED).body(user.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<Usuario> putUser(@RequestBody Usuario user) {
        Optional<Usuario> updateUser = userService.updateUser(user);
        if (updateUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(updateUser.get());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable long id) {
        Optional<Usuario> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
        }
    }

    @PutMapping("/cadastrar-moderador/{id}")
    public ResponseEntity<CreateModeratorResponse> putCreateModerator(@PathVariable int id) {

        CreateModeratorResponse response = userService.createModerator(id);
        return ResponseEntity.status(201).body(response);
    }

}
