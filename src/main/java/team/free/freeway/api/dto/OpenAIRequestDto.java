package team.free.freeway.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAIRequestDto {

    private String model;
    private List<MessageDto> messages;

    public OpenAIRequestDto(String model, List<MessageDto> messages) {
        this.model = model;
        this.messages = messages;
    }
}
