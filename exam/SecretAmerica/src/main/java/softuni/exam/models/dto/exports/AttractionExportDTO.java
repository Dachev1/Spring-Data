package softuni.exam.models.dto.exports;

public class AttractionExportDTO {
    private Long id;
    private String name;
    private String description;
    private Integer elevation;
    private String countryName;

    // Default Constructor
    public AttractionExportDTO() {
    }

    // Parameterized Constructor
    public AttractionExportDTO(Long id, String name, String description, Integer elevation, String countryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.elevation = elevation;
        this.countryName = countryName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return String.format("***%s - %s at an altitude of %dm. somewhere in %s.",
                name, description, elevation, countryName);
    }
}
