package softuni.exam.models.dto.imports.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordsRootDTO {

    @XmlElement(name = "borrowing_record")
    private List<BorrowingRecordSeedDTO> records;

    public List<BorrowingRecordSeedDTO> getRecords() {
        return records;
    }

    public void setRecords(List<BorrowingRecordSeedDTO> records) {
        this.records = records;
    }
}
