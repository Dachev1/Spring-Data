package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.SellerSeedDTO;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {
    private final static String FILE_PATH = "src/main/resources/files/json/sellers.json";

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException {
        String jsonContent = this.readSellersFromFile();

        SellerSeedDTO[] sellerDTOs = this.gson.fromJson(jsonContent, SellerSeedDTO[].class);

        StringBuilder result = new StringBuilder();

        for (SellerSeedDTO sellerDTO : sellerDTOs) {
            if (!this.validationUtil.isValid(sellerDTO)) {
                result.append("Invalid seller").append(System.lineSeparator());
                continue;
            }

            Seller seller = this.modelMapper.map(sellerDTO, Seller.class);

            try {
                this.sellerRepository.save(seller);
                result.append(String.format(
                        "Successfully imported seller %s %s",
                        seller.getFirstName(),
                        seller.getLastName()
                )).append(System.lineSeparator());
            } catch (DataIntegrityViolationException e) {
                result.append("Invalid seller").append(System.lineSeparator());
            }
        }

        return result.toString().trim();
    }

}
