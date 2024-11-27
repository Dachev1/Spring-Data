package softuni.exam.models.dto.imports.xml.offer;

import javax.xml.bind.annotation.XmlElement;

public class AgentNameWrapper {

    private String name;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
