package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.LibraryMember;

import javax.validation.constraints.Size;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
}
