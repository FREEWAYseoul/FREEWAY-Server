package team.free.freeway.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CrawlerTest {

    @Autowired
    TwitterCrawler twitterCrawler;

    @Test
    void test() throws Exception {
        List<NotificationDto> notifications = twitterCrawler.selenium();
        for (NotificationDto notification : notifications) {
        }
    }
}