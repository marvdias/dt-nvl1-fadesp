package com.fadesp.mdias.fadesp_dt_nvl1.model;
import java.math.BigDecimal;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.DecimalMin;

import com.fadesp.mdias.fadesp_dt_nvl1.dto.PagamentoDTO;

import lombok.Data;

@Data
@Entity
@Table(name = "pagamentos")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @NotNull
    @Column(name = "cpf_cnpj")
    @Pattern(regexp = "\\d{11,14}")
    private String cpfCnpj;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "valor")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(30) default 'PENDENTE_PROCESSAMENTO'")
    private StatusPagamento status;

	public Pagamento() {

	}

    public Pagamento(PagamentoDTO pagamento) {
        this.cpfCnpj = pagamento.getCpfCnpj();
        this.metodoPagamento = pagamento.getMetodoPagamento();
        this.numeroCartao = pagamento.getNumeroCartao();
        this.valor = pagamento.getValor();
        this.status = pagamento.getStatus();
    }

	public Pagamento(String cpfCnpj, MetodoPagamento metodoPagamento, String numeroCartao, BigDecimal valor, StatusPagamento status) {
		this.cpfCnpj = cpfCnpj;
		this.metodoPagamento = metodoPagamento;
		this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.status = status;
	}


}