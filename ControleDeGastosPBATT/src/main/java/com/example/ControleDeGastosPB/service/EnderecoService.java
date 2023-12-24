package com.example.ControleDeGastosPB.service;

import com.example.ControleDeGastosPB.clients.IEnderecoClient;
import com.example.ControleDeGastosPB.modelo.Categoria;
import com.example.ControleDeGastosPB.modelo.Classificacao;
import com.example.ControleDeGastosPB.modelo.Endereco;
import com.example.ControleDeGastosPB.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoClient enderecoClient;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarEnderecoPorId(Long enderecoId) {
        return enderecoRepository.findById(enderecoId);
    }

    public Endereco buscarCep(String cep) {
        try {
            return enderecoClient.buscarCep(cep);
        } catch (Exception e) {
            // Lidar com exceções, logar, retornar um valor padrão, etc.
            e.printStackTrace();
            return null;
        }
    }

    public Endereco buscarEnderecoPorCep(String cep) {
        return enderecoRepository.findByCep(cep);
    }

    public Endereco adicionarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public void excluirEndereco(Long enderecoId) {
        enderecoRepository.deleteById(enderecoId);
    }
}

