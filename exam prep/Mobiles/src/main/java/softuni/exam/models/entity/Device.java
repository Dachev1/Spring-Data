package softuni.exam.models.entity;

import softuni.exam.models.enums.DeviceType;

import javax.persistence.*;

@Entity
@Table(name = "devices")
public class Device extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(nullable = false, unique = true)
    private String model;

    @Basic
    private Double price;

    @Basic
    private Integer storage;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
