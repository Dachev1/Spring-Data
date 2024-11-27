package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.exports.BestOfferExportDTO;
import softuni.exam.models.dto.imports.xml.offer.OfferRootDTO;
import softuni.exam.models.dto.imports.xml.offer.OfferSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.validation.ValidationUtil;
import softuni.exam.util.xmlParser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public OfferServiceImpl(OfferRepository offerRepository, ApartmentRepository apartmentRepository, AgentRepository agentRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.apartmentRepository = apartmentRepository;
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        OfferRootDTO offerRootDTO = this.xmlParser.fromFile(FILE_PATH, OfferRootDTO.class);

        for (OfferSeedDTO dto : offerRootDTO.getOffers()) {
            if (!this.validationUtil.isValid(dto)) {
                sb.append("Invalid offer").append(System.lineSeparator());
                continue;
            }

            Long apartmentId = dto.getApartment().getId();
            Optional<Apartment> apartment = this.apartmentRepository.findById(apartmentId);
            String agentName = dto.getAgent().getName();
            Optional<Agent> agent = this.agentRepository.findByFirstName(agentName);

            if (apartment.isEmpty() || agent.isEmpty()) {
                sb.append("Invalid offer").append(System.lineSeparator());
                continue;
            }

            Offer offer = this.modelMapper.map(dto, Offer.class);
            offer.setApartment(apartment.get());
            offer.setAgent(agent.get());
            this.offerRepository.save(offer);

            sb.append(String.format("Successfully imported offer - %.2f", offer.getPrice()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();

        List<BestOfferExportDTO> bestOffers = this.offerRepository.findBestOffers();

        for (BestOfferExportDTO offer : bestOffers) {
            sb.append(String.format(
                    "Agent %s with offer №%d:%n" +
                            "    -Apartment area: %.2f%n" +
                            "    --Town: %s%n" +
                            "    ---Price: %.2f$%n",
                    offer.getAgentFullName(),
                    offer.getOfferId(),
                    offer.getArea(),
                    offer.getTownName(),
                    offer.getPrice()
            ));
        }

        return sb.toString();
    }

}
