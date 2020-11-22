package doc.num.projet.model;
import java.util.Date;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Donation extends Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToMany
    List<Objects> listMsgSnd = new ArrayList<Objects>();
    String idPrevMsg;

    public Donation() {
        super();
    }

    public Donation(Date date, Date dateV, String idPrevMsg, List<Objects> sndObjectList) {
        super(date, dateV);
        this.idPrevMsg = idPrevMsg;
        this.listMsgSnd = sndObjectList;
        this.roles.add(AttributRole.DONATION);
    }

    public Donation(Date date, Date dateV, String idPrevMsg, Objects sndObject) {
        super(date, dateV);
        this.idPrevMsg = idPrevMsg;
        this.listMsgSnd.add(sndObject);
        this.roles.add(AttributRole.DONATION);
    }

}