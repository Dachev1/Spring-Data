package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.SaleSeedDTO;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SaleRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SaleServiceImpl implements SaleService {

    private final static String FILE_PATH = "src/main/resources/files/json/sales.json";

    private final SaleRepository saleRepository;
    private final SellerRepository sellerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public SaleServiceImpl(SaleRepository saleRepository, SellerRepository sellerRepository,
                           ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.saleRepository = saleRepository;
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;

        // Simplified registration of LocalDateTime adapter
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (com.google.gson.JsonDeserializer<LocalDateTime>) (json, type, context) ->
                                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .create();
    }

    @Override
    public boolean areImported() {
        return this.saleRepository.count() > 0;
    }

    @Override
    public String readSalesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSales() throws IOException {
        String jsonContent = this.readSalesFileContent();

        SaleSeedDTO[] saleDTOs = this.gson.fromJson(jsonContent, SaleSeedDTO[].class);

        StringBuilder result = new StringBuilder();

        for (SaleSeedDTO saleDTO : saleDTOs) {
            if (!this.validationUtil.isValid(saleDTO) || saleDTO.getNumber().length() != 7 ||
                    this.saleRepository.existsByNumber(saleDTO.getNumber())) {
                result.append("Invalid sale").append(System.lineSeparator());
                continue;
            }

            Seller seller = this.sellerRepository.findById(saleDTO.getSeller()).orElse(null);

            if (seller == null) {
                result.append("Invalid sale").append(System.lineSeparator());
                continue;
            }

            Sale sale = this.modelMapper.map(saleDTO, Sale.class);
            sale.setSeller(seller);

            this.saleRepository.save(sale);

            result.append(String.format(
                    "Successfully imported sale with number %s",
                    sale.getNumber()
            )).append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
