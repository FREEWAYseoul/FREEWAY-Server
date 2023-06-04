package team.free.freeway.service;

import team.free.freeway.controller.dto.StationListResponseDto;

import java.util.List;

public interface StationService {

    List<StationListResponseDto> searchStationsByName(String keyword);
}
