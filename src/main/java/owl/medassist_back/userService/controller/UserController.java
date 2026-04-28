package owl.medassist_back.userService.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import owl.medassist_back.userService.dto.RegisterRequestDto;
import owl.medassist_back.userService.dto.UserDto;
import owl.medassist_back.userService.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @RequestBody RegisterRequestDto request,
            HttpServletResponse response
    ) {
        UserDto user = userService.registerUser(request);

        return ResponseEntity.status(201).body(user);
    }

}
