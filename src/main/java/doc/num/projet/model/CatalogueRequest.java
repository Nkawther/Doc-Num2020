package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CatalogueRequest extends Message {
   

    public CatalogueRequest(Date date, Date dateV) {
        super(date, dateV);

        this.roles.add(AttributRole.CATREQUEST);
    }

}
