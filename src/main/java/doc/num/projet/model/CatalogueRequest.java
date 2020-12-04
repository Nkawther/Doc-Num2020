package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CatalogueRequest extends Message {

    public CatalogueRequest() {
        super();
        this.roles.add(AttributRole.CATREQUEST);
    }

    public CatalogueRequest(Date date, Date dateV, Long idHeader) {
        super(date, dateV, idHeader);

        this.roles.add(AttributRole.CATREQUEST);
    }

}
