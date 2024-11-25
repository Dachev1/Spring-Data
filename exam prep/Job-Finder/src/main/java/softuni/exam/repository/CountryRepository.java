package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Country;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {


    boolean existsByName(String name);

    Optional<Country> findByName(String country);
}
