package io.redspark.wikireddev.controller;

import io.redspark.wikireddev.GitHub.GithubProvider;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private GithubProvider github;

    private GHRepository ghRepository;

    @GetMapping
    public String teste() throws IOException {
        github.getConnection().getMyOrganizations();



        return "Testes";
    }
}
