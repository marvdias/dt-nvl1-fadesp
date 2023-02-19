package com.fadesp.mdias.fadesp_dt_nvl1.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadesp.mdias.fadesp_dt_nvl1.dto.PagamentoDTO;
import com.fadesp.mdias.fadesp_dt_nvl1.model.MetodoPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.Pagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.StatusPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.service.PagamentoService;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {
    
    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.listarPagamentos();
        List<PagamentoDTO> pagamentosDTO = pagamentos.stream().map(PagamentoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPagamentoPorId(@PathVariable Long id) {
        Optional<Pagamento> pagamentoOpt = pagamentoService.buscarPagamentoPorId(id);

        if (pagamentoOpt.isPresent()) {
            PagamentoDTO pagamentoDTO = new PagamentoDTO(pagamentoOpt.get());
            return ResponseEntity.ok(pagamentoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento(pagamentoDTO);
        try {
            Pagamento novoPagamento = pagamentoService.criarPagamento(pagamento);
            pagamentoDTO = new PagamentoDTO(novoPagamento);        
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new PagamentoDTO(e.getMessage()));
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizarPagamento(@PathVariable Long id, @RequestBody PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento(pagamentoDTO);
        Pagamento pagamentoAtualizado = pagamentoService.atualizarStatusPagamento(id, pagamento);

        if (pagamentoAtualizado != null) {
            pagamentoDTO = new PagamentoDTO(pagamentoAtualizado);
            return ResponseEntity.ok(pagamentoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id) {
        boolean pagamentoDeletado = pagamentoService.deletarPagamento(id);

        if (pagamentoDeletado) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/cpf_cnpj/{cpfCnpj}")
    public ResponseEntity<List<PagamentoDTO>> buscarPagamentosPorCpfOuCnpj(@PathVariable String cpfCnpj) {
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentosPorCpfOuCnpj(cpfCnpj);
        List<PagamentoDTO> pagamentosDTO = pagamentos.stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);
    }

    @GetMapping("/metodo_pagamento/{metodoPagamento}")
    public ResponseEntity<List<PagamentoDTO>> buscarPorMetodoPagamento(@PathVariable String metodoPagamento) {
        MetodoPagamento enumMetodoPagamento;
        try {
            enumMetodoPagamento = MetodoPagamento.valueOf(metodoPagamento.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentosPorMetodoPagamento(enumMetodoPagamento);
        List<PagamentoDTO> pagamentosDTO = pagamentos.stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);
    }

    @GetMapping("/status/{statusPagamento}")
    public ResponseEntity<List<PagamentoDTO>> buscarPorStatusPagamento(@PathVariable String statusPagamento) {
        StatusPagamento enumStatusPagamento;
        try {
            enumStatusPagamento = StatusPagamento.valueOf(statusPagamento.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentosPorStatusPagamento(enumStatusPagamento);
        List<PagamentoDTO> pagamentosDTO = pagamentos.stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);
    }
    
}