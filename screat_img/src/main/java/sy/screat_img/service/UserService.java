package sy.screat_img.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sy.screat_img.entity.User;
import sy.screat_img.repository.UserRepository;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User newuser = User.builder()
                .username(user.getUsername())
                .password(encodedPassword)
                .build();
        userRepository.save(newuser);
    }
}
