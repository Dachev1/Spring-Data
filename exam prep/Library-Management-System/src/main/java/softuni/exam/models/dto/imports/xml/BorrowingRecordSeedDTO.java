package softuni.exam.models.dto.imports.xml;

import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordSeedDTO {

    @XmlElement(name = "borrow_date")
    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate borrowDate;

    @XmlElement(name = "return_date")
    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate returnDate;

    @XmlElement(name = "remarks")
    @Size(min = 3, max = 100)
    private String remarks;

    @XmlElement(name = "book")
    @NotNull
    private BorrowingBookDTO book;

    @XmlElement(name = "member")
    @NotNull
    private BorrowingMemberDTO member;

    public @NotNull LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(@NotNull LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public @NotNull LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(@NotNull LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public @Size(min = 3, max = 100) String getRemarks() {
        return remarks;
    }

    public void setRemarks(@Size(min = 3, max = 100) String remarks) {
        this.remarks = remarks;
    }

    public BorrowingBookDTO getBook() {
        return book;
    }

    public void setBook(BorrowingBookDTO book) {
        this.book = book;
    }

    public BorrowingMemberDTO getMember() {
        return member;
    }

    public void setMember(BorrowingMemberDTO member) {
        this.member = member;
    }
}