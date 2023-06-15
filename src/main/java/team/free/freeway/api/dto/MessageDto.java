package team.free.freeway.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDto {

    private String role;
    private String content;

    public MessageDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
