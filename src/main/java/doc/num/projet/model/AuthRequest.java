package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuthRequest extends Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public AuthRequest(Date date, Date dateV) {
        super(date, dateV);
        this.roles.add(AttributRole.AUTHREQUEST);

    }
}
