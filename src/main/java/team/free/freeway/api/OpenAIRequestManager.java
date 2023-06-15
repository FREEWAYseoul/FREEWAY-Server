package team.free.freeway.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team.free.freeway.api.dto.MessageDto;
import team.free.freeway.api.dto.OpenAIRequestDto;
import team.free.freeway.api.dto.OpenAIResponseDto;
import team.free.freeway.domain.Notification;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class OpenAIRequestManager {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL_NAME = "gpt-3.5-turbo";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.key}")
    private String openAIKey;

    public String getNotificationSummary(Notification notification) throws JsonProcessingException {
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIKey);

        String requestBody = objectMapper.writeValueAsString(createRequestBodyObject(notification.getContent()));
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<OpenAIResponseDto> responseEntity =
                restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, requestEntity, OpenAIResponseDto.class);

        return responseEntity.getBody().getOpenAIResponses().get(0).getOpenAIMessages().getContent();
    }

    private OpenAIRequestDto createRequestBodyObject(String content) {
        MessageDto systemMessage = new MessageDto("system", "다음 내용을 중요 키워드만 사용하여 짧고 간단한 한글 제목으로 요약");
        MessageDto userMessage = new MessageDto("user", content);
        List<MessageDto> messages = List.of(systemMessage, userMessage);

        return new OpenAIRequestDto(MODEL_NAME, messages);
    }
}
