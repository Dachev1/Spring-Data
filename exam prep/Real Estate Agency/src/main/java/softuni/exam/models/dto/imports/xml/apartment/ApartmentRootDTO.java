package softuni.exam.models.dto.imports.xml.apartment;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentRootDTO {
    @XmlElement(name = "apartment")
    private List<ApartmentSeedDTO> apartments;

    public List<ApartmentSeedDTO> getApartments() {
        return apartments;
    }

    public void setApartments(List<ApartmentSeedDTO> apartments) {
        this.apartments = apartments;
    }
}
