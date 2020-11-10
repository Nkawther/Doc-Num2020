package doc.num.projet.model;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;;

@Data
@Entity
public class Object {

    @Id
    String objectName;
    String objectDetails;
    // ImageIO objectImage;

    public Object() {

    }

    public Object(String objectName, String objectDetails, ImageIO objectImage) {
        this.objectName = objectName;
        this.objectDetails = objectDetails;
        // this.objectImage=objectImage;
    }

    public Object(String objectName) {
        this.objectName = objectName;
    }

    public Object(String objectName, String objectDetails) {
        this.objectName = objectName;
        this.objectDetails = objectDetails;
    }

}