package doc.num.projet.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Catalogue extends Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToMany
    List<Object> catObjectList = new ArrayList<Object>();

    Date catDate;

    public Catalogue(Date date, Date dateV, Date catDate, List<Object> catObjectList) {
        super(date, dateV);
        this.catDate = catDate;
        this.catObjectList = catObjectList;
        this.roles.add(AttributRole.CAT);
    }

    public Catalogue(Date date, Date dateV, Date catDate, Object unObjectList) {
        super(date, dateV);
        this.catDate = catDate;
        this.catObjectList.add(unObjectList);
        this.roles.add(AttributRole.CAT);
    }

}
