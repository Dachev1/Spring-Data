package com.example.football.models.dto.imports.xml.stat;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatRootDTO {

    @XmlElement(name = "stat")
    private List<StatDTO> stats;

    public List<StatDTO> getStats() {
        return stats;
    }

    public void setStats(List<StatDTO> stats) {
        this.stats = stats;
    }
}
