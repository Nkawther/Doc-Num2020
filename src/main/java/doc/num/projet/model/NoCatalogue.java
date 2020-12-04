package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class NoCatalogue extends Message {

    String reason;
    String idCatRequestMsg;

    public NoCatalogue() {
        super();
        this.roles.add(AttributRole.NOCAT);
    }

    public NoCatalogue(Date date, Date dateV, String reason, Long idHeader, String idCat) {
        super(date, dateV, idHeader);
        this.reason = reason;
        this.idCatRequestMsg = idCat;
        this.roles.add(AttributRole.NOCAT);
    }

}
