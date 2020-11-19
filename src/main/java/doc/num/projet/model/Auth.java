package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Auth extends Message {

    Date authDate;

    public Auth(Date date, Date validity) {
        super(date, validity);
        this.authDate = date;
    }
}
