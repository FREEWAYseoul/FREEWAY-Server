package team.free.freeway.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.api.dto.value.OpenAIResponse;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAIResponseDto {

    @JsonAlias(value = "choices")
    private List<OpenAIResponse> openAIResponses;
}
