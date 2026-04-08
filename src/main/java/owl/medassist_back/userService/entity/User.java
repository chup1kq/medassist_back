package owl.medassist_back.userService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof User)) return false;

        User other = (User) o;
        return Objects.equals(login, other.login);
    }
}
