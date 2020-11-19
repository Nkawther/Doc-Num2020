package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Accept extends Message {
  
    String idPropositionMsg;

    public Accept(Date date, Date validity, String idPropositionMsg) {
        super(date, validity);
        this.idPropositionMsg = idPropositionMsg;
    }
}
