package com.fadesp.mdias.fadesp_dt_nvl1.dto;

import java.math.BigDecimal;

import com.fadesp.mdias.fadesp_dt_nvl1.model.MetodoPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.Pagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class PagamentoDTO {

    @JsonInclude(Include.NON_NULL)
    private Long id;
    @JsonInclude(Include.NON_NULL)
    private String cpfCnpj;
    @JsonInclude(Include.NON_NULL)
    private MetodoPagamento metodoPagamento;
    @JsonInclude(Include.NON_NULL)
    private String numeroCartao;
    @JsonInclude(Include.NON_NULL)
    private BigDecimal valor;
    @JsonInclude(Include.NON_NULL)
    private StatusPagamento status;
    @JsonInclude(Include.NON_NULL)
    private String error;

    public PagamentoDTO() {
    }
    public PagamentoDTO(String error) {
        this.error = error;
    }
    public PagamentoDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.cpfCnpj = pagamento.getCpfCnpj();
        this.metodoPagamento = pagamento.getMetodoPagamento();
        this.numeroCartao = pagamento.getNumeroCartao();
        this.valor = pagamento.getValor();
        this.status = pagamento.getStatus();
    }

    public PagamentoDTO(Long id, String cpfCnpj, MetodoPagamento metodoPagamento, String numeroCartao, BigDecimal valor, StatusPagamento status) {
        this.id = id;
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.status = status;
    }
}
