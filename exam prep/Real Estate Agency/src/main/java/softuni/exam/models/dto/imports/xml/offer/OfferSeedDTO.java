package softuni.exam.models.dto.imports.xml.offer;

import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDTO {
    @NotNull
    @Min(0)
    @XmlElement
    private BigDecimal price;

    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "publishedOn")
    private LocalDate publishedOn;

    @NotNull
    @XmlElement
    private ApartmentIdWrapper apartment;

    @NotNull
    @XmlElement
    private AgentNameWrapper agent;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public ApartmentIdWrapper getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentIdWrapper apartment) {
        this.apartment = apartment;
    }

    public AgentNameWrapper getAgent() {
        return agent;
    }

    public void setAgent(AgentNameWrapper agent) {
        this.agent = agent;
    }
}
