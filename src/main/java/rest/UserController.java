package rest;

import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public List<User> listAll() {
        List<User> users = userRepository.findAll();
        return users;
    }


}
