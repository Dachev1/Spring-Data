package lab.relations;

import jakarta.persistence.*;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String number;

    public PlateNumber() {}

    public PlateNumber(String number) {
        this.number = number;
    }
}
