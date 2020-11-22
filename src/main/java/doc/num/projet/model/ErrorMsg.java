package doc.num.projet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class ErrorMsg extends Message{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    //id message et id de l'erreur

    public ErrorMsg(){

        this.roles.add(AttributRole.ERRORMSG);
    }


}