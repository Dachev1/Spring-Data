package softuni.exam.models.dto.imports.xml.personalData;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "personal_datas")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDataRootDTO {

    @XmlElement(name = "personal_data")
    private List<PersonalDataDTO> personalDataList;

    public List<PersonalDataDTO> getPersonalDataList() {
        return personalDataList;
    }

    public void setPersonalDataList(List<PersonalDataDTO> personalDataList) {
        this.personalDataList = personalDataList;
    }
}
