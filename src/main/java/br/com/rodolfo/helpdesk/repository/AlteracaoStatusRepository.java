package br.com.rodolfo.helpdesk.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rodolfo.helpdesk.models.AlteracaoStatus;

/**
 * AlteracaoStatusRepository
 */
public interface AlteracaoStatusRepository extends MongoRepository<AlteracaoStatus, String>{

    Iterable<AlteracaoStatus> findByTicketIdOrderByDataMudancaStatusDesc(String ticketId);
    
}