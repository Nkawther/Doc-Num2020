package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Deny extends Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String idPropositionMsg;
    String reason;

    public Deny(Date date, Date validity, String idPropositionMsg, String reason) {
        super(date, validity);
        this.idPropositionMsg = idPropositionMsg;
        this.reason = reason;
    }
}