package doc.num.projet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Barter extends Message {

    @OneToMany
    List<Objects> rcvObjectList = new ArrayList<Objects>();
    @OneToMany
    List<Objects> sndObjectList = new ArrayList<Objects>();
    String idPrevMsg;

    public Barter() {
        super();
        this.roles.add(AttributRole.BARTER);
    }

    public Barter(Date date, Date dateV, String idPrevMsg, List<Objects> rcvObjectList, List<Objects> sndObjectList,
            Long idHeader) {
        super(date, dateV, idHeader);
        this.idPrevMsg = idPrevMsg;
        this.rcvObjectList = rcvObjectList;
        this.sndObjectList = sndObjectList;
        this.roles.add(AttributRole.BARTER);
    }

    public Barter(Date date, Date dateV, String idPrevMsg, Objects rcvObject, Objects sndObject, Long idHeader) {
        super(date, dateV, idHeader);
        this.idPrevMsg = idPrevMsg;
        this.rcvObjectList.add(rcvObject);
        this.sndObjectList.add(sndObject);
        this.roles.add(AttributRole.BARTER);
    }

    public void addrcv(Objects o) {
        this.rcvObjectList.add(o);
    }

    public void addsnd(Objects o) {
        this.sndObjectList.add(o);
    }
}
