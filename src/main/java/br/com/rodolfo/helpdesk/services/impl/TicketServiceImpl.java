package br.com.rodolfo.helpdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.rodolfo.helpdesk.models.AlteracaoStatus;
import br.com.rodolfo.helpdesk.models.Ticket;
import br.com.rodolfo.helpdesk.repositories.AlteracaoStatusRepository;
import br.com.rodolfo.helpdesk.repositories.TicketRepository;
import br.com.rodolfo.helpdesk.services.TicketService;

/**
 * TicketServiceImpl
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AlteracaoStatusRepository alteracaoStatusRepository;

    @Override
    public Ticket createOrUpdate(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(String id) {
        return this.ticketRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    public Page<Ticket> listTicket(int pag, int qtd) {
        return this.ticketRepository.findAll(PageRequest.of(pag, qtd));
    }

    @Override
    public AlteracaoStatus createAlteracaoStatus(AlteracaoStatus alteracaoStatus) {
        return this.alteracaoStatusRepository.save(alteracaoStatus);
    }

    @Override
    public Iterable<AlteracaoStatus> listAlteracaoStatus(String ticketId) {
        return this.alteracaoStatusRepository.findByTicketIdOrderByDataMudancaStatusDesc(ticketId);
    }

    @Override
    public Page<Ticket> findByCurrentUser(int pag, int qtd, String usuarioId) {
        return this.ticketRepository.findByUsuarioIdOrderByDataDesc(PageRequest.of(pag, qtd), usuarioId);
    }

    @Override
    public Page<Ticket> findByParametros(int pag, int qtd, String titulo, String status, String prioridade) {
        return this.ticketRepository.findByTituloIgnoreCaseContainingAndStatusAndPrioridadeOrderByDataDesc(titulo, status, prioridade, PageRequest.of(pag, qtd));
    }

    @Override
    public Page<Ticket> findByParametrosAndCurrentUser(int pag, int qtd, String titulo, String status,
            String prioridade, String usuarioId) {
        return this.ticketRepository.findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioIdOrderByDataDesc(titulo, status, prioridade, usuarioId, PageRequest.of(pag, qtd));
    }

    @Override
    public Page<Ticket> findByNumero(int pag, int qtd, Integer numero) {
        return this.ticketRepository.findByNumero(numero, PageRequest.of(pag, qtd));
    }

    @Override
    public Iterable<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Page<Ticket> findByParametrosAndUsuarioDesignado(int pag, int qtd, String titulo, String status,
			String prioridade, String usuarioDesignadoId) {
		return this.ticketRepository.findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioDesignadoIdOrderByDataDesc(titulo, status, prioridade, usuarioDesignadoId, PageRequest.of(pag, qtd));
	}

    
}