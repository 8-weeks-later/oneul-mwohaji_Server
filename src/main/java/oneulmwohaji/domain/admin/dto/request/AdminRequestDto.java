package oneulmwohaji.domain.admin.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String oauthId;
}
