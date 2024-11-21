package softuni.exam.models.dto.imports;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class SaleSeedDTO {

    private Boolean discounted;

    @NotNull
    @NotBlank
    @Size(min = 7, max = 7) // Ensures the number has exactly 7 characters
    private String number;

    @NotNull // Sale date cannot be null
    private LocalDateTime saleDate;

    @NotNull // Seller ID cannot be null
    private Long seller;

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

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }
}
