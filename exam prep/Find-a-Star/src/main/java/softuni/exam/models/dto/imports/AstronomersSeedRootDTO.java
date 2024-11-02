package softuni.exam.models.dto.imports;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomersSeedRootDTO {

    @XmlElement(name = "astronomer")
    List<AstronomerSeedDTO> astronomerSeedDTOList;

    public List<AstronomerSeedDTO> getAstronomers() {
        return astronomerSeedDTOList;
    }

    public void setAstronomers(List<AstronomerSeedDTO> astronomerSeedDTOList) {
        this.astronomerSeedDTOList = astronomerSeedDTOList;
    }
}
