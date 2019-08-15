package br.com.rodolfo.helpdesk.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.rodolfo.helpdesk.models.AlteracaoStatus;
import br.com.rodolfo.helpdesk.models.Ticket;

/**
 * TicketService
 */
// Classe será gerenciada pelo Spring
@Component
public interface TicketService {

    Ticket createOrUpdate(Ticket ticket);

    Ticket findById(String id);

    void delete(String id);

    Page<Ticket> listTicket(int pag, int qtd);

    // Guardar o LOG de alterações no ticket
    AlteracaoStatus createAlteracaoStatus(AlteracaoStatus alteracaoStatus);

    // Retorna uma lista de alterações que o ticket já sofreu
    Iterable<AlteracaoStatus> listAlteracaoStatus(String ticketId);

    // Quando o usuário procurar por tickets, irá retorna apenas os abertos por ele
    Page<Ticket> findByCurrentUser(int pag, int qtd, String usuarioId);

    // Realizar uma consulta personalizada do ticket
    Page<Ticket> findByParametros(int pag, int qtd, String titulo, String status, String prioridade);
    
    // Realizar uma consulta personalizada do ticket para o usuário em questão
    Page<Ticket> findByParametrosAndCurrentUser(int pag, int qtd, String titulo, String status, String prioridade, String usuarioId);

    Page<Ticket> findByNumero(int pag, int qtd, Integer numero);

    // Faz um resumo de todos os tickets
    Iterable<Ticket> findAll();

    // Buscar todos os tickets designados para o usuário técnico 
    Page<Ticket> findByParametrosAndUsuarioDesignado(int pag, int qtd, String titulo, String status, String prioridade, String usuarioDesignadoId);
}