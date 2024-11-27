package softuni.exam.models.dto.exports;

public class BestOfferExportDTO {
    private String agentFullName;
    private Long offerId;
    private Double area;
    private String townName;
    private Double price;

    public BestOfferExportDTO(String agentFullName, Long offerId, Double area, String townName, Double price) {
        this.agentFullName = agentFullName;
        this.offerId = offerId;
        this.area = area;
        this.townName = townName;
        this.price = price;
    }

    public String getAgentFullName() {
        return agentFullName;
    }

    public void setAgentFullName(String agentFullName) {
        this.agentFullName = agentFullName;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

