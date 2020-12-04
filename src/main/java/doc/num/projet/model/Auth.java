package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Auth extends Message {

    Date authDate;

    public Auth() {
        super();
        this.roles.add(AttributRole.AUTH);
    }

    public Auth(Date date, Date validity, Long idHeader) {
        super(date, validity, idHeader);
        this.authDate = date;
        this.roles.add(AttributRole.AUTH);
    }
}
