package doc.num.projet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ErrorMsg extends Message{

    String idMsg;
    String idError;

    public ErrorMsg(){
        super();
    }
    public ErrorMsg(String idMsg, String idError){
        super();
        this.idMsg=idMsg;
        this.idError=idError;
        this.roles.add(AttributRole.ERRORMSG);
    }


}