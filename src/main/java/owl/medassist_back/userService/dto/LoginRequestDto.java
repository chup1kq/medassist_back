package owl.medassist_back.userService.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String login,
        @NotBlank String password
) {
}
