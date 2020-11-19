package doc.num.projet.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Catalogue extends Message {

    @OneToMany
    List<Objects> catObjectList = new ArrayList<Objects>();

    Date catDate;

    public Catalogue(Date date, Date dateV, Date catDate, List<Objects> catObjectList, Long idHeader) {
        super(date, dateV, idHeader);
        this.catDate = catDate;
        this.catObjectList = catObjectList;
        this.roles.add(AttributRole.CAT);
    }

    public Catalogue(Date date, Date dateV, Date catDate, Objects unObjectList, Long idHeader) {
        super(date, dateV, idHeader);
        this.catDate = catDate;
        this.catObjectList.add(unObjectList);
        this.roles.add(AttributRole.CAT);
    }

}
