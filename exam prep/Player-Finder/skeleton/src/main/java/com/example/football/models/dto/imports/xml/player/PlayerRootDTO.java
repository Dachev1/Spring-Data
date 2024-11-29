package com.example.football.models.dto.imports.xml.player;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerRootDTO {

    @XmlElement(name = "player")
    private List<PlayerDTO> players;

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
