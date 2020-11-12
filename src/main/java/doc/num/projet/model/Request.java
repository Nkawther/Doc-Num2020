package doc.num.projet.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Request extends Message{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToMany
    List<Object> rcvObjectList = new ArrayList<Object>();
    String idPrevMsg;

    public Request(){
        super();
    }
    public Request(Date date, Date dateV, String idPrevMsg, List<Object> rcvObjectList){
        super(date, dateV);
        this.idPrevMsg=idPrevMsg;
        this.rcvObjectList=rcvObjectList;
        this.roles.add(AttributRole.REQUEST);
    }
    public Request(Date date, Date dateV, String idPrevMsg, Object rcvObject){
        super(date, dateV);
        this.idPrevMsg=idPrevMsg;
        this.rcvObjectList.add(rcvObject);
        this.roles.add(AttributRole.REQUEST);
    }

}