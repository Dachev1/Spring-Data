package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsByBrandAndModel(String brand, String model);

    @Query("SELECT d FROM Device d WHERE d.deviceType = 'SMART_PHONE' AND d.price < 1000 AND d.storage >= 128 ORDER BY LOWER(d.brand)")
    List<Device> findSmartphonesWithPriceBelowAndStorageAbove();
}
