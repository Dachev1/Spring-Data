package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.exports.AttractionExportDTO;
import softuni.exam.models.entity.Attraction;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    boolean existsByName(String name);

    @Query("SELECT new softuni.exam.models.dto.exports.AttractionExportDTO(a.id, a.name, a.description, a.elevation, a.country.name) " +
            "FROM Attraction a WHERE a.type IN (:types) AND a.elevation >= :minElevation " +
            "ORDER BY a.name ASC, a.country.name ASC")
    List<AttractionExportDTO> findAttractionsForExport(
            @Param("types") List<String> types, @Param("minElevation") int minElevation);

}
