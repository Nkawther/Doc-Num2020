package doc.num.projet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Header {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    int nbMsg;
    Long idTransmitter;
    Long idReceiver;
    String authRef;
    Date authDate;
    @OneToMany
    List<Message> lsMessage = new ArrayList<>();

    public Header() {

    }

    public Header(int nbMsg, Long idTransmitter, Long idReceiver, String authRef, Date authDate) {
        this.nbMsg = nbMsg;
        this.idTransmitter = idTransmitter;
        this.idReceiver = idReceiver;
        this.authRef = authRef;
        this.authDate = authDate;
    }

}
