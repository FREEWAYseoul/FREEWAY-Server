package team.free.freeway.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.Elevator;
import team.free.freeway.domain.Facilities;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.value.Coordinate;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationDetailsResponseDto {

    private String stationId;
    private String stationName;
    private String lineId;
    private String lineName;
    private Coordinate stationCoordinate;
    private String stationStatus;
    private String stationContact;
    private String stationImageUrl;
    private String nextStation;
    private String previousStation;
    private FacilitiesDto facilities;
    private List<ElevatorListResponseDto> elevators;

    @Builder
    public StationDetailsResponseDto(
            String stationId, String stationName, String lineId, String lineName, Coordinate stationCoordinate,
            String stationStatus, String stationContact, String stationImageUrl, String nextStation,
            String previousStation, FacilitiesDto facilities, List<ElevatorListResponseDto> elevators
    ) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.lineId = lineId;
        this.lineName = lineName;
        this.stationCoordinate = stationCoordinate;
        this.stationStatus = stationStatus;
        this.stationContact = stationContact;
        this.stationImageUrl = stationImageUrl;
        this.nextStation = nextStation;
        this.previousStation = previousStation;
        this.facilities = facilities;
        this.elevators = elevators;
    }

    public static StationDetailsResponseDto from(Station station) {
        List<Elevator> elevators = station.getElevators();
        List<ElevatorListResponseDto> elevatorList = new ArrayList<>();
        for (Elevator elevator : elevators) {
            ElevatorListResponseDto elevatorListResponseDto = ElevatorListResponseDto.from(elevator);
            elevatorList.add(elevatorListResponseDto);
        }

        FacilitiesDto facilitiesDto = FacilitiesDto.from(station.getFacilities());

        return StationDetailsResponseDto.builder()
                .stationId(station.getId())
                .stationName(station.getName())
                .lineId(station.getSubwayLine().getId())
                .lineName(station.getSubwayLine().getLineName())
                .stationCoordinate(station.getCoordinate())
                .stationStatus(station.getStatus().getStatusName())
                .stationContact(station.getContact())
                .stationImageUrl(station.getImageUrl())
                .facilities(facilitiesDto)
                .elevators(elevatorList)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class ElevatorListResponseDto {

        private Long elevatorId;
        private Coordinate elevatorCoordinate;
        private String elevatorStatus;

        @Builder
        public ElevatorListResponseDto(Long elevatorId, Coordinate elevatorCoordinate, String elevatorStatus) {
            this.elevatorId = elevatorId;
            this.elevatorCoordinate = elevatorCoordinate;
            this.elevatorStatus = elevatorStatus;
        }

        public static ElevatorListResponseDto from(Elevator elevator) {
            return ElevatorListResponseDto.builder()
                    .elevatorId(elevator.getId())
                    .elevatorCoordinate(elevator.getCoordinate())
                    .elevatorStatus(elevator.getStatus().getStatusName())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class FacilitiesDto {

        private Boolean elevator;
        private Boolean wheelchairLift;
        private Boolean disabledToilet;
        private Boolean transitParkingLot;
        private Boolean unmannedCivilApplicationIssuingMachine;
        private Boolean currencyExchangeKiosk;
        private Boolean trainTicketOffice;
        private Boolean feedingRoom;

        @Builder
        public FacilitiesDto(Boolean elevator, Boolean wheelchairLift, Boolean disabledToilet,
                             Boolean transitParkingLot, Boolean unmannedCivilApplicationIssuingMachine,
                             Boolean currencyExchangeKiosk, Boolean trainTicketOffice, Boolean feedingRoom) {
            this.elevator = elevator;
            this.wheelchairLift = wheelchairLift;
            this.disabledToilet = disabledToilet;
            this.transitParkingLot = transitParkingLot;
            this.unmannedCivilApplicationIssuingMachine = unmannedCivilApplicationIssuingMachine;
            this.currencyExchangeKiosk = currencyExchangeKiosk;
            this.trainTicketOffice = trainTicketOffice;
            this.feedingRoom = feedingRoom;
        }

        public static FacilitiesDto from(Facilities facilities) {
            return FacilitiesDto.builder()
                    .elevator(facilities.getElevator())
                    .wheelchairLift(facilities.getWheelchairLift())
                    .disabledToilet(facilities.getDisabledToilet())
                    .transitParkingLot(facilities.getTransitParkingLot())
                    .unmannedCivilApplicationIssuingMachine(facilities.getUnmannedCivilApplicationIssuingMachine())
                    .currencyExchangeKiosk(facilities.getCurrencyExchangeKiosk())
                    .trainTicketOffice(facilities.getTrainTicketOffice())
                    .feedingRoom(facilities.getFeedingRoom())
                    .build();
        }
    }
}
