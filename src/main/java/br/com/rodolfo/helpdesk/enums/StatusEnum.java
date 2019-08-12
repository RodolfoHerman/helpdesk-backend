package br.com.rodolfo.helpdesk.enums;

public enum StatusEnum {

    NOVO,
    DESIGNADO,
    RESOLVIDO,
    APROVADO,
    RECUSADO,
    FECHADO;

    public static StatusEnum toEnum(String status) {
        
        if(status == null) {

            return null;
        }

        for(StatusEnum descricao: StatusEnum.values()) {

            if(descricao.toString().equals(status)) {

                return descricao;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + status);
    }
}