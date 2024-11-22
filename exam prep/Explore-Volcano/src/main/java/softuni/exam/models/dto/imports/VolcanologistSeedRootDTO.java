package softuni.exam.models.dto.imports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedRootDTO {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistSeedDTO> volcanologists;

    public List<VolcanologistSeedDTO> getVolcanologists() {
        return volcanologists;
    }

    public void setVolcanologists(List<VolcanologistSeedDTO> volcanologists) {
        this.volcanologists = volcanologists;
    }
}