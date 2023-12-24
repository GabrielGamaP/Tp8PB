package com.example.ControleDeGastosPB.controller;

import com.example.ControleDeGastosPB.modelo.Endereco;
import com.example.ControleDeGastosPB.modelo.Resultado;
import com.example.ControleDeGastosPB.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/buscarCep/{cep}")
    public Endereco buscarCep(@PathVariable String cep) {
        return enderecoService.buscarCep(cep);
    }

    @GetMapping("/{cep}")
    public Endereco buscarEnderecoPorCep(@PathVariable String cep) {
        return enderecoService.buscarEnderecoPorCep(cep);
    }

    @PostMapping
    public Endereco adicionarEndereco(@RequestBody Endereco endereco) {
        return enderecoService.adicionarEndereco(endereco);
    }

    @DeleteMapping("/excluir/{enderecoId}")
    public Resultado excluirEndereco(@PathVariable Long enderecoId) {
        enderecoService.excluirEndereco(enderecoId);
        return new Resultado();
    }
}

