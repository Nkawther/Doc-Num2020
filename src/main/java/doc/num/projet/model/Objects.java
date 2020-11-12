package doc.num.projet.model;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Objects {

    @Id
    String objectName;
    String objectDetails;
    // ImageIO objectImage;

    public Objects() {

    }

    public Objects(String objectName, String objectDetails, ImageIO objectImage) {
        this.objectName = objectName;
        this.objectDetails = objectDetails;
        // this.objectImage=objectImage;
    }

    public Objects(String objectName) {
        this.objectName = objectName;
    }

    public Objects(String objectName, String objectDetails) {
        this.objectName = objectName;
        this.objectDetails = objectDetails;
    }

}
