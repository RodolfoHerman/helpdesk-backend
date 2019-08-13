package br.com.rodolfo.helpdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rodolfo.helpdesk.models.Ticket;

/**
 * TicketRepository
 */
public interface TicketRepository extends MongoRepository<Ticket, String>{

    Page<Ticket> findByUsuarioIdOrderByDataDesc(Pageable pages, String UsuarioId);

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingOrderByDataDesc(
        String titulo, String status, String prioridade, Pageable pages
    );

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndUsuarioIdOrderByDataDesc(
        String titulo, String status, String prioridade, Pageable pages
    );

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusIgnoreCaseContainingAndPrioridadeIgnoreCaseContainingAndUsuarioDesignadoIdOrderByDataDesc(
        String titulo, String status, String prioridade, Pageable pages
    );

    Page<Ticket> findByNumero(Integer numero, Pageable pages);
}