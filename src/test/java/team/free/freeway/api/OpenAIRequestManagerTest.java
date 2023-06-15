package team.free.freeway.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.free.freeway.domain.Notification;

import java.time.LocalDateTime;

@SpringBootTest
class OpenAIRequestManagerTest {

    @Autowired
    OpenAIRequestManager openAIRequestManager;

    @Test
    void test() throws Exception {
        openAIRequestManager.getNotificationSummary(Notification.builder()
                .content("안내말씀 드립니다. 코레일 운영구간인 수인분당선 선로 장애로 왕십리역~선릉역 상하행 열차운행이 일시 중단되었으니, 환승 이용객들께서는 이 점 참고하여 열차를 이용해 주시기 바랍니다. 자세한 사항은 코레일로 문의주시기 바랍니다.")
                .dateTime(LocalDateTime.now())
                .build());
    }
}