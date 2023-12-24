package com.example.ControleDeGastosPB.clients;

import com.example.ControleDeGastosPB.modelo.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "enderecoClient", url = "https://viacep.com.br/ws")
public interface IEnderecoClient {

    @GetMapping(value = "/{cep}/json/")
    Endereco buscarCep(@PathVariable String cep);
}