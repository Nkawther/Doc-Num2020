package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;


import lombok.Data;

@Data
@Entity
public class ErrorMsg extends Message{

    String idMsg;
    String idError;

    public ErrorMsg(){
        super();
    }
    public ErrorMsg(String idMsg, String idError, Date date, Date dateV, Long idHeader){
        super(date, dateV, idHeader);
        this.idMsg=idMsg;
        this.idError=idError;
        this.roles.add(AttributRole.ERRORMSG);
    }


}