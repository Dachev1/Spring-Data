package softuni.exam.models.dto.imports.xml.visitor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "visitors")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorRootDTO {

    @XmlElement(name = "visitor")
    private List<VisitorDTO> visitors;

    public List<VisitorDTO> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<VisitorDTO> visitors) {
        this.visitors = visitors;
    }
}
