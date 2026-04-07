package owl.medassist_back.userService.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank String login,
        @NotBlank String password
) {
}
