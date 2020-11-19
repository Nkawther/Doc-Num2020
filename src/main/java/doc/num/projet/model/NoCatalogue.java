package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class NoCatalogue extends Message {

    String reason;

    public NoCatalogue(Date date, Date dateV, String reason, Long idHeader) {
        super(date, dateV, idHeader);
        this.reason = reason;
        this.roles.add(AttributRole.NOCAT);
    }

}
