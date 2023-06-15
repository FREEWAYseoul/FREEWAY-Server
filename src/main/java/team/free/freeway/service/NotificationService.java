package team.free.freeway.service;

import team.free.freeway.controller.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    void updateSubwayNotification();

    List<NotificationResponseDto> getNotificationsWithinLast14Days();
}