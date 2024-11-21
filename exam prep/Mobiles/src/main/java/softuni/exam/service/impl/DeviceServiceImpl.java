package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.xml.DeviceSeedDTO;
import softuni.exam.models.dto.imports.xml.DevicesRootDTO;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.Sale;
import softuni.exam.repository.DeviceRepository;
import softuni.exam.repository.SaleRepository;
import softuni.exam.service.DeviceService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final static String FILE_PATH = "src/main/resources/files/xml/devices.xml";

    private final DeviceRepository deviceRepository;
    private final SaleRepository saleRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository, SaleRepository saleRepository,
                             ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.deviceRepository = deviceRepository;
        this.saleRepository = saleRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.deviceRepository.count() > 0;
    }

    @Override
    public String readDevicesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importDevices() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        DevicesRootDTO rootDTO = this.xmlParser.fromFile(FILE_PATH, DevicesRootDTO.class);

        for (DeviceSeedDTO deviceDTO : rootDTO.getDevices()) {
            // Check if the saleId is null
            if (deviceDTO.getSaleId() == null) {
                sb.append("Invalid device").append(System.lineSeparator());
                continue;
            }

            // Check if the device already exists
            boolean deviceExists = this.deviceRepository.existsByBrandAndModel(deviceDTO.getBrand(), deviceDTO.getModel());

            // Check if the sale exists
            Optional<Sale> saleOptional = this.saleRepository.findById(deviceDTO.getSaleId());

            if (!this.validationUtil.isValid(deviceDTO) || deviceExists || saleOptional.isEmpty()) {
                sb.append("Invalid device").append(System.lineSeparator());
                continue;
            }

            // Map the DTO to a Device entity
            Device device = this.modelMapper.map(deviceDTO, Device.class);
            device.setSale(saleOptional.get());

            // Save the Device entity
            this.deviceRepository.saveAndFlush(device);

            sb.append(String.format("Successfully imported device of type %s with brand %s",
                            device.getDeviceType(), device.getBrand()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }


    @Override
    public String exportDevices() {
        StringBuilder sb = new StringBuilder();

        List<Device> devices = this.deviceRepository.findSmartphonesWithPriceBelowAndStorageAbove();

        for (Device device : devices) {
            sb.append(String.format("Device brand: %s%n", device.getBrand()))
                    .append(String.format("   *Model: %s%n", device.getModel()))
                    .append(String.format("   **Storage: %d%n", device.getStorage()))
                    .append(String.format("   ***Price: %.2f%n", device.getPrice()));
        }

        return sb.toString().trim();
    }
}
