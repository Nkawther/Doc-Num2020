package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class AuthRequest extends Message {

    public AuthRequest(){
        super();
    }
    public AuthRequest(Date date, Date validity, Long idHeader) {
        super(date, validity, idHeader);
        this.roles.add(AttributRole.AUTHREQUEST);
    }
}
