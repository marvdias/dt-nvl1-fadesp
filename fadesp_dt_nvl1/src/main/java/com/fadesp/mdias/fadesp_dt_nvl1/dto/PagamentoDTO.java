package com.fadesp.mdias.fadesp_dt_nvl1.dto;

import java.math.BigDecimal;

import com.fadesp.mdias.fadesp_dt_nvl1.model.MetodoPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.Pagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.StatusPagamento;

import lombok.Data;

@Data
public class PagamentoDTO {
    
    private Long id;
    private String cpfCnpj;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao;
    private BigDecimal valor;
    private StatusPagamento status;

    public PagamentoDTO() {
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
