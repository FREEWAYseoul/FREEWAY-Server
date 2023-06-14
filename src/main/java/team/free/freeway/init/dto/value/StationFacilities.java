package team.free.freeway.init.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationFacilities {

    @JsonAlias(value = "STATION_ID")
    private String stationCode;

    @JsonAlias(value = "STATION_NAME")
    private String stationName;

    @JsonAlias(value = "LINE")
    private String lineName;

    @JsonAlias(value = "EL")
    private String elevator;

    @JsonAlias(value = "WL")
    private String wheelchairLift;

    @JsonAlias(value = "PARKING")
    private String transitParkingLot;

    @JsonAlias(value = "CIM")
    private String unmannedCivilApplicationIssuingMachine;

    @JsonAlias(value = "EXCHANGE")
    private String currencyExchangeKiosk;

    @JsonAlias(value = "TRAIN")
    private String trainTicketOffice;

    @JsonAlias(value = "FDROOM")
    private String feedingRoom;

    public boolean hasElevator() {
        return elevator.equals("Y");
    }

    public boolean hasWheelchairLift() {
        return wheelchairLift.equals("Y");
    }

    public boolean hasTransitParkingLot() {
        return transitParkingLot.equals("Y");
    }

    public boolean hasUnmannedCivilApplicationIssuingMachine() {
        return unmannedCivilApplicationIssuingMachine.equals("Y");
    }

    public boolean hasCurrencyExchangeKiosk() {
        return currencyExchangeKiosk.equals("Y");
    }

    public boolean hasTrainTicketOffice() {
        return trainTicketOffice.equals("Y");
    }

    public boolean hasFeedingRoom() {
        return feedingRoom.equals("Y");
    }
}
