package subway.station.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.station.repository.entity.StationEntity;
import subway.station.repository.StationRepository;
import subway.station.web.StationRequest;
import subway.station.web.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StationService {
    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    public StationResponse saveStation(StationRequest stationRequest) {
        StationEntity station = stationRepository.save(new StationEntity(stationRequest.getName()));
        return createStationResponse(station);
    }

    public List<StationResponse> findAllStations() {
        return stationRepository.findAll().stream()
                .map(this::createStationResponse)
                .collect(Collectors.toList());
    }

    public StationResponse findStation(Long stationId) {
        return stationRepository.findById(stationId).map(this::createStationResponse).orElseGet(null);
    }


    @Transactional
    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }

    private StationResponse createStationResponse(StationEntity station) {
        return new StationResponse(
                station.getId(),
                station.getName()
        );
    }
}