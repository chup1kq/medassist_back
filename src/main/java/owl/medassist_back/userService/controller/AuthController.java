package owl.medassist_back.userService.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.userService.dto.AuthResponseDto;
import owl.medassist_back.userService.dto.LoginRequestDto;
import owl.medassist_back.userService.dto.LoginResultDto;
import owl.medassist_back.userService.dto.UserDto;
import owl.medassist_back.userService.service.AuthService;
import org.springframework.security.core.Authentication;
import owl.medassist_back.userService.entity.User;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(
            @RequestBody LoginRequestDto request,
            HttpServletResponse response
    ) {

        LoginResultDto loginResult = authService.login(request);
        addCookie(response, loginResult.tokens());

        return ResponseEntity.ok(loginResult.user());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {

        AuthResponseDto tokens = authService.refreshToken(refreshToken);
        addCookie(response, tokens);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        deleteCookie(response, "accessToken");
        deleteCookie(response, "refreshToken");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(new UserDto(user.getId(), user.getLogin()));
    }

    private void addCookie(HttpServletResponse response, AuthResponseDto authResponse) {
        addCookie(response, "accessToken", authResponse.accessToken(), authResponse.accessTokenExpiration());
        addCookie(response, "refreshToken", authResponse.refreshToken(), authResponse.refreshTokenExpiration());
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {

        Cookie cookie = new Cookie(name, null);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
