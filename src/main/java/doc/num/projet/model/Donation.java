package doc.num.projet.model;

import java.util.Date;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;

import java.util.List;

@Data
@Entity
public class Donation extends Message {
    @OneToMany
    List<Objects> listMsgSnd = new ArrayList<Objects>();
    String idPrevMsg;

    public Donation() {
        super();
    }

    public Donation(Date date, Date dateV, String idPrevMsg, List<Objects> sndObjectList, Long idHeader) {
        super(date, dateV, idHeader);
        this.idPrevMsg = idPrevMsg;
        this.listMsgSnd = sndObjectList;
        this.roles.add(AttributRole.DONATION);
    }

    public Donation(Date date, Date dateV, String idPrevMsg, Objects sndObject, Long idHeader) {
        super(date, dateV, idHeader);
        this.idPrevMsg = idPrevMsg;
        this.listMsgSnd.add(sndObject);
        this.roles.add(AttributRole.DONATION);
    }

}