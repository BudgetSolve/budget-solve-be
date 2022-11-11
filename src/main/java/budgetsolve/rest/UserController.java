package budgetsolve.rest;

import budgetsolve.model.User;
import budgetsolve.repository.UserRepository;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        Preconditions.checkArgument(user.getId() == null, "New user ID should be null");
        return userRepository.save(user);
    }
}
