package doc.num.projet.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Barter extends Message{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToMany
    List<Object> rcvObjectList = new ArrayList<Object>();
    @OneToMany
    List<Object> sndObjectList = new ArrayList<Object>();
    Long idPrevMsg;

    public Barter(){

    }
    public Barter(Long idPrevMsg, List<Object> rcvObjectList, List<Object> sndObjectList){
        this.idPrevMsg=idPrevMsg;
        this.rcvObjectList=rcvObjectList;
        this.sndObjectList=sndObjectList;
        this.roles.add(AttributRole.BARTER);
    }

    public Barter(Long idPrevMsg, Object rcvObject, Object sndObject){
        this.idPrevMsg=idPrevMsg;
        this.rcvObjectList.add(rcvObject);
        this.sndObjectList.add(sndObject);
        this.roles.add(AttributRole.BARTER);
    }
}
