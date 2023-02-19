package com.fadesp.mdias.fadesp_dt_nvl1;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.fadesp.mdias.fadesp_dt_nvl1.model.MetodoPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.Pagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.model.StatusPagamento;
import com.fadesp.mdias.fadesp_dt_nvl1.service.PagamentoService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FadespDtNvl1ApplicationTest {

    @Autowired
    private PagamentoService pagamentoService;

    @Test
    public void testCriarPagamento() {
        // Cria um novo pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpj("12345678900");
        pagamento.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamento.setNumeroCartao("1234567890123456");
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);

        // Salva o pagamento no banco de dados
        Pagamento pagamentoSalvo = pagamentoService.criarPagamento(pagamento);

        // Verifica se o ID foi gerado corretamente
        assertTrue(pagamentoSalvo.getId() > 0);
        
    }

    @Test
    public void testBuscarPagamento() {
        // Cria um novo pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpj("12345678900");
        pagamento.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamento.setNumeroCartao("1234567890123456");
        pagamento.setValor(new BigDecimal("100.00"));

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setCpfCnpj("12345678900");
        pagamento2.setMetodoPagamento(MetodoPagamento.pix);
        pagamento2.setValor(new BigDecimal("150.00"));

        Pagamento pagamento3 = new Pagamento();
        pagamento3.setCpfCnpj("12345678912");
        pagamento3.setMetodoPagamento(MetodoPagamento.pix);
        pagamento3.setValor(new BigDecimal("250.00"));


        // Salva o pagamento no banco de dados
        Pagamento pagamentoSalvo = pagamentoService.criarPagamento(pagamento);
        Pagamento pagamentoSalvo2 = pagamentoService.criarPagamento(pagamento2);
        Pagamento pagamentoSalvo3 = pagamentoService.criarPagamento(pagamento3);

        // Atualiza o pagamento
        pagamentoSalvo.setStatus(StatusPagamento.PROCESSADO_COM_SUCESSO);
        pagamentoService.atualizarStatusPagamento(pagamentoSalvo.getId(), pagamentoSalvo);

        // Busca o pagamento pelo ID
        Pagamento pagamentoEncontrado1 = pagamentoService.buscarPagamentoPorId(pagamentoSalvo.getId()).orElse(null);
        // Busca o pagamento por CPF/CNPJ
        List<Pagamento> pagamentosEncontrados2 = pagamentoService.buscarPagamentosPorCpfOuCnpj("12345678900");
        // Busca o pagamento por Status
        List<Pagamento> pagamentosEncontrados3 = pagamentoService.buscarPagamentosPorStatusPagamento(StatusPagamento.PENDENTE_PROCESSAMENTO);

        // Verifica se o pagamento foi encontrado
        assertNotNull(pagamentoEncontrado1);
        assertThat(pagamentosEncontrados2).hasSize(2);
        assertThat(pagamentosEncontrados3).extracting("id").containsExactlyInAnyOrder(pagamentoSalvo2.getId(), pagamentoSalvo3.getId());


    }

    @Test
    public void testAtualizarPagamento() {
        // Cria um novo pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpj("12345678900");
        pagamento.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamento.setNumeroCartao("1234567890123456");
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);

        // Salva o pagamento no banco de dados
        Pagamento pagamentoSalvo = pagamentoService.criarPagamento(pagamento);

        // Atualiza o pagamento
        pagamentoSalvo.setStatus(StatusPagamento.PROCESSADO_COM_SUCESSO);
        Pagamento pagamentoAtualizado = pagamentoService.atualizarStatusPagamento(pagamentoSalvo.getId(), pagamentoSalvo);

        // Verifica se o pagamento foi atualizado
        assertEquals(StatusPagamento.PROCESSADO_COM_SUCESSO, pagamentoAtualizado.getStatus());
    }

    @Test
    public void testDeletarPagamento() {

        // cria um pagamento com status "Pendente de processamento"
        Pagamento pagamento = new Pagamento();
        pagamento.setCpfCnpj("11111111111");
        pagamento.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamento.setNumeroCartao("1111111111111111");
        pagamento.setValor(new BigDecimal("100.00"));

        Pagamento novoPagamento = pagamentoService.criarPagamento(pagamento);

        // tenta deletar o pagamento e verifica se o pagamento ainda existe no banco de dados com o status "Pendente de processamento"
        pagamentoService.deletarPagamento(novoPagamento.getId());
        assertNull(pagamentoService.buscarPagamentoPorId(novoPagamento.getId()).orElse(null));

        Pagamento novoPagamento2 = pagamentoService.criarPagamento(pagamento);

        // atualiza o status do pagamento para "Processado com sucesso"
        novoPagamento2.setStatus(StatusPagamento.PROCESSADO_COM_SUCESSO);
        pagamentoService.atualizarStatusPagamento(novoPagamento2.getId(), novoPagamento2);

        // tenta deletar o pagamento novamente e verifica se a operação falhou e o pagamento ainda existe no banco de dados com o status "Processado com sucesso"
        try {
            pagamentoService.deletarPagamento(novoPagamento2.getId());
            // Falhou ao tentar deletar, pagamento ainda deve existir
            assertNotNull(pagamentoService.buscarPagamentoPorId(novoPagamento2.getId()).orElse(null));
        } catch (Exception e) {
            assertNotNull(pagamentoService.buscarPagamentoPorId(novoPagamento2.getId()).orElse(null));
            assertEquals(StatusPagamento.PROCESSADO_COM_SUCESSO, pagamentoService.buscarPagamentoPorId(novoPagamento2.getId()).orElse(null).getStatus());
        }
    }
}