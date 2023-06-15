package team.free.freeway.api.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAIResponse {

    @JsonAlias(value = "message")
    private OpenAIMessage openAIMessages;
}
