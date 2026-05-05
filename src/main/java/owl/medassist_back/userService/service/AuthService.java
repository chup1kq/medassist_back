package owl.medassist_back.userService.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import owl.medassist_back.userService.dto.AuthResponseDto;
import owl.medassist_back.userService.dto.LoginRequestDto;
import owl.medassist_back.userService.dto.LoginResultDto;
import owl.medassist_back.userService.dto.UserDto;
import owl.medassist_back.userService.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtService jwtService;

    private final UserService userService;

    public LoginResultDto login(@NotNull LoginRequestDto request) {

        User user = safelyRemove(userService.findByLogin(request.login()));

        AuthResponseDto response = jwtService.generateTokens(user);
        UserDto userDto = new UserDto(user.getId(), user.getLogin());
        log.info("User {} logged in", user.getLogin());

        return new LoginResultDto(response, userDto);
    }

    public AuthResponseDto refreshToken(@NotNull String refreshToken) {

        String login = jwtService.extractLogin(refreshToken);
        User user = safelyRemove(userService.findByLogin(login));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");    // TODO custom exception
        }

        AuthResponseDto response = jwtService.generateTokens(user);
        log.info("User {} refreshed tokens", user.getLogin());

        return response;
    }

    private User safelyRemove(Optional<User> user) {
        return user.orElseThrow(() -> new RuntimeException("User not found"));  // TODO custom exception
    }
}
