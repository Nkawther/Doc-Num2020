package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Deny extends Message {
    String idPropositionMsg;
    String reason;

    public Deny(Date date, Date validity, String idPropositionMsg, String reason) {
        super(date, validity);
        this.idPropositionMsg = idPropositionMsg;
        this.reason = reason;
    }
}
