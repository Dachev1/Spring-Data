package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @Basic
    private Boolean discounted;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @OneToMany(mappedBy = "sale")
    private Set<Device> devices;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(discounted, sale.discounted) && Objects.equals(number, sale.number) && Objects.equals(saleDate, sale.saleDate) && Objects.equals(devices, sale.devices) && Objects.equals(seller, sale.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discounted, number, saleDate, devices, seller);
    }
}
