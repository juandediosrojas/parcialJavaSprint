package proyecto.com.example.parcial.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "admin123";
        String hashedPassword = encoder.encode(password);

        System.out.println(hashedPassword);
    }

}
