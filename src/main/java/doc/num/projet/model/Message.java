package doc.num.projet.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Date date;
    Date validity;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    public Set<AttributRole> roles = new HashSet<>();
    Long idHeader;

    public Message() {

    }

    public Message(Date date, Date validity, Long idHeader) {
        this.date = date;
        this.validity = validity;
        this.roles.add(AttributRole.MESSAGE);
        this.idHeader = idHeader;
    }
}