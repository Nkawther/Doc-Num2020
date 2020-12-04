package doc.num.projet.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Catalogue extends Message {

    @OneToMany
    List<Objects> catObjectList = new ArrayList<Objects>();
    String idCatRequestMsg;
    Date catDate;

    public Catalogue() {
        super();
        this.roles.add(AttributRole.CAT);
    }

    public Catalogue(Date date, Date dateV, Date catDate, List<Objects> catObjectList, Long idHeader, String idcat) {
        super(date, dateV, idHeader);
        this.catDate = catDate;
        this.catObjectList = catObjectList;
        this.idCatRequestMsg = idcat;
        this.roles.add(AttributRole.CAT);
    }

    public Catalogue(Date date, Date dateV, Date catDate, Objects unObjectList, Long idHeader, String idcat) {
        super(date, dateV, idHeader);
        this.catDate = catDate;
        this.catObjectList.add(unObjectList);
        this.idCatRequestMsg = idcat;
        this.roles.add(AttributRole.CAT);
    }

    public void add(Objects o) {
        this.catObjectList.add(o);
    }

}
