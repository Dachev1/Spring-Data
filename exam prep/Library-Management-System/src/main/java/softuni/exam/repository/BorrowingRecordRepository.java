package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.exports.BorrowingRecordExportDTO;
import softuni.exam.models.entity.BorrowingRecord;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query("SELECT new softuni.exam.models.dto.exports.BorrowingRecordExportDTO(" +
            "br.book.title, " +
            "br.book.author, " +
            "br.borrowDate, " +
            "br.member.firstName, " +
            "br.member.lastName) " +
            "FROM BorrowingRecord br " +
            "WHERE br.borrowDate < :date " +
            "AND br.book.genre = 'SCIENCE_FICTION' " +
            "ORDER BY br.borrowDate DESC")
    List<BorrowingRecordExportDTO> findBorrowingRecordsBeforeDate(@Param("date") LocalDate date);
}
