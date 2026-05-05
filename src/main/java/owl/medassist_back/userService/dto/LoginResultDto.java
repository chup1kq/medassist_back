package owl.medassist_back.userService.dto;

public record LoginResultDto(
        AuthResponseDto tokens,
        UserDto user
) {
}

