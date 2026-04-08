package owl.medassist_back.userService.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank Integer id,
        @NotBlank String login
) {
}
