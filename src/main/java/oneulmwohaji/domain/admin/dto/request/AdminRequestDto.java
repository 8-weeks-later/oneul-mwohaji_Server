package oneulmwohaji.domain.admin.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String oauthId;
}
