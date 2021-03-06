package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Accept extends Message {

    String idPropositionMsg;

    public Accept() {
        super();
        this.roles.add(AttributRole.ACCEPT);
    }

    public Accept(Date date, Date validity, String idPropositionMsg, Long idHeader) {
        super(date, validity, idHeader);
        this.idPropositionMsg = idPropositionMsg;
        this.roles.add(AttributRole.ACCEPT);
    }
}
