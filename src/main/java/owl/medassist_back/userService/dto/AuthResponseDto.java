package owl.medassist_back.userService.dto;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {
}
