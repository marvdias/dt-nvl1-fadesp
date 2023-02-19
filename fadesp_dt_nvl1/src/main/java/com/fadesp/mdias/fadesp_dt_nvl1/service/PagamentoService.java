package com.fadesp.mdias.fadesp_dt_nvl1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fadesp.mdias.fadesp_dt_nvl1.model.MetodoPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.Pagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.StatusPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.repository.PagamentoRepository;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> buscarPagamentosPorCpfOuCnpj(String cpfOuCnpj) {
        return pagamentoRepository.findByCpfCnpj(cpfOuCnpj);
    }

    public List<Pagamento> buscarPagamentosPorMetodoPagamento(MetodoPagamento metodoPagamento) {
        return pagamentoRepository.findByMetodoPagamento(metodoPagamento);
    }

    public List<Pagamento> buscarPagamentosPorStatusPagamento(StatusPagamento statusPagamento) {
        return pagamentoRepository.findByStatus(statusPagamento);
    }


    public Pagamento criarPagamento(Pagamento pagamento) {
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
        if(pagamento.getMetodoPagamento() == MetodoPagamento.boleto || pagamento.getMetodoPagamento() == MetodoPagamento.pix){
            pagamento.setNumeroCartao(null);
        }
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizarStatusPagamento(Long id, Pagamento pagamento) {
        Pagamento pagamentoExistente = buscarPagamentoPorId(id).orElse(null);

        if (pagamentoExistente != null) {
            StatusPagamento statusAtual = pagamentoExistente.getStatus();
            StatusPagamento novoStatus = pagamento.getStatus();
            
            switch (statusAtual) {
                case PENDENTE_PROCESSAMENTO:  // Pode ser atualizado para PROCESSADO_COM_SUCESSO PROCESSADO_COM_FALHA
                    if (novoStatus == StatusPagamento.PROCESSADO_COM_SUCESSO || novoStatus == StatusPagamento.PROCESSADO_COM_FALHA) {
                        pagamentoExistente.setStatus(novoStatus);
                    }
                    break;
                case PROCESSADO_COM_FALHA:   // Pode ser atualizado para PENDENTE_PROCESSAMENTO
                    if (novoStatus == StatusPagamento.PENDENTE_PROCESSAMENTO) {
                        pagamentoExistente.setStatus(novoStatus);
                    }
                    break;
                default:
                    return null;
            }
            return pagamentoRepository.save(pagamentoExistente);
        } else {
            return null;
        }
    }

    public boolean deletarPagamento(Long id) {
        Pagamento pagamentoExistente = buscarPagamentoPorId(id).orElse(null);
        
        if (pagamentoExistente != null && pagamentoExistente.getStatus() == StatusPagamento.PENDENTE_PROCESSAMENTO) {
            pagamentoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}