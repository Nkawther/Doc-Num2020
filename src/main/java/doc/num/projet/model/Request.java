package doc.num.projet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Request extends Message {

    @OneToMany
    List<Objects> listMsgRcv = new ArrayList<Objects>();
    String idPrevMsg;

    public Request() {
        super();
    }

    public Request(Date date, Date dateV, String idPrevMsg, List<Objects> rcvObjectList, Long idHeader) {
        super(date, dateV, idHeader);
        this.idPrevMsg = idPrevMsg;
        this.listMsgRcv = rcvObjectList;
        this.roles.add(AttributRole.REQUEST);
    }

    public Request(Date date, Date dateV, String idPrevMsg, Objects rcvObject, Long idHeader) {
        super(date, dateV, idHeader);
        this.idPrevMsg = idPrevMsg;
        this.listMsgRcv.add(rcvObject);
        this.roles.add(AttributRole.REQUEST);
    }

}