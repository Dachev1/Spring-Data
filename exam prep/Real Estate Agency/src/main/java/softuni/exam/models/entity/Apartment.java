package softuni.exam.models.entity;

import softuni.exam.models.enums.ApartmentType;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Apartment extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApartmentType apartmentType;

    @Column(nullable = false)
    private double area;

    @ManyToOne
    @JoinColumn(name = "town_id", nullable = false)
    private Town town;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private Set<Offer> offers;

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
}