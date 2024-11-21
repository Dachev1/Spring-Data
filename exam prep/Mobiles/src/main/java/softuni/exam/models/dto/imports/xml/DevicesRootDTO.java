package softuni.exam.models.dto.imports.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "devices")
@XmlAccessorType(XmlAccessType.FIELD)
public class DevicesRootDTO {

    @XmlElement(name = "device")
    private List<DeviceSeedDTO> devices;

    public List<DeviceSeedDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceSeedDTO> devices) {
        this.devices = devices;
    }
}