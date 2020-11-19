package doc.num.projet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ErrorMsg extends Message{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String idMsg;
    String idError;

    public ErrorMsg(){

        this.roles.add(AttributRole.ERRORMSG);
    }


}