package br.com.rodolfo.helpdesk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rodolfo.helpdesk.models.Ticket;

/**
 * TicketRepository
 */
public interface TicketRepository extends MongoRepository<Ticket, String>{

    Page<Ticket> findByUsuarioIdOrderByDataDesc(Pageable pages, String usuarioId);

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeOrderByDataDesc(
        String titulo, String status, String prioridade, Pageable pages
    );

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioIdOrderByDataDesc(
        String titulo, String status, String prioridade, String usuarioId, Pageable pages
    );

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioDesignadoIdOrderByDataDesc(
        String titulo, String status, String prioridade, String usuarioDesignadoId, Pageable pages
    );

    Page<Ticket> findByNumero(Integer numero, Pageable pages);
}