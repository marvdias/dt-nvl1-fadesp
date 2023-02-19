package com.fadesp.mdias.fadesp_dt_nvl1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fadesp.mdias.fadesp_dt_nvl1.model.MetodoPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.Pagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.StatusPagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByCpfCnpj(String cpfCnpj);

    List<Pagamento> findByMetodoPagamento(MetodoPagamento metodoPagamento);

    List<Pagamento> findByStatus(StatusPagamento status);
}
