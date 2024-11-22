package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcanologist;

@Repository
public interface VolcanologistRepository extends JpaRepository<Volcanologist, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
