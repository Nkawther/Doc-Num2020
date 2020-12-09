package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Auth extends Message {

    Date authDate;
    String authRef;

    public Auth() {
        super();
        this.roles.add(AttributRole.AUTH);
    }

    public Auth(String authRef, Date date, Date dateRef, Date validity, Long idHeader) {
        super(date, validity, idHeader);
        this.authDate = dateRef;
        this.authRef = authRef;
        this.roles.add(AttributRole.AUTH);
    }
}
