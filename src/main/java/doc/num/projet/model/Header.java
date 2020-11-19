package doc.num.projet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Header {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    int nbMsg;
    String nameTransmitter;
    String nameReceiver;
    String authRef;
    Date authDate;

    public Header() {

    }

    public Header(int nbMsg, String nameTransmitter, String nameReceiver, String authRef, Date authDate) {
        this.nbMsg = nbMsg;
        this.nameTransmitter = nameTransmitter;
        this.nameReceiver = nameReceiver;
        this.authRef = authRef;
        this.authDate = authDate;
    }

}
