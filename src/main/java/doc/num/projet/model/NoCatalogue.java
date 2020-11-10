package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NoCatalogue extends Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String reason;

    public NoCatalogue(Date date, Date dateV, String reason) {
        super(date, dateV);
        this.reason = reason;
        this.roles.add(AttributRole.NOCAT);
    }

}
