package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Auth extends Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Date authDate;

    public Auth(Date date, Date validity) {
        super(date, validity);
        this.authDate = date;
    }
}
