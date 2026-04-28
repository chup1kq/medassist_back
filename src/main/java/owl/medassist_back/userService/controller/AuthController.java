package owl.medassist_back.userService.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.userService.dto.AuthResponseDto;
import owl.medassist_back.userService.dto.LoginRequestDto;
import owl.medassist_back.userService.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequestDto request,
            HttpServletResponse response
    ) {

        AuthResponseDto tokens = authService.login(request);
        addCookie(response, tokens);

        return ResponseEntity.ok().build();
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

    private void addCookie(HttpServletResponse response, AuthResponseDto authResponse) {
        addCookie(response, "accessToken", authResponse.accessToken(), authResponse.accessTokenExpiration());
        addCookie(response, "refreshToken", authResponse.refreshToken(), authResponse.refreshTokenExpiration());
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {

        Cookie cookie = new Cookie(name, value);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/auth");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {

        Cookie cookie = new Cookie(name, null);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/auth");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
