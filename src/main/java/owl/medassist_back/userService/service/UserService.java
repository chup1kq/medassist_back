package owl.medassist_back.userService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import owl.medassist_back.userService.dto.RegisterRequestDto;
import owl.medassist_back.userService.dto.UserDto;
import owl.medassist_back.userService.entity.User;
import owl.medassist_back.userService.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(RegisterRequestDto request) {

        if (existsByLogin(request.login())) {
            throw new RuntimeException("User already exists");  // TODO custom exception
        }

        User user = userRepository.save(
                new User(request.login(), passwordEncoder.encode(request.password()))
        );
        log.info("User {} registered", user.getLogin());

        return new UserDto(user.getId(), user.getLogin());
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

}
