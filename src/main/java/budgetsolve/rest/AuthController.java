package budgetsolve.rest;

import budgetsolve.filter.JwtUtils;
import budgetsolve.model.User;
import budgetsolve.pojo.auth.LoginRequest;
import budgetsolve.pojo.auth.LoginResponse;
import budgetsolve.pojo.register.RegisterRequest;
import budgetsolve.repository.UserRepository;
import budgetsolve.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @PostMapping("/authorise")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (BadCredentialsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            userRepository.save(new User(
                    registerRequest.getFirstName(),
                    registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword())));
            return new ResponseEntity<>("User was created successfully", HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Error while creating user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reming-password")
    public ResponseEntity<?> remindPassword(@RequestBody String email) {
        try {
            User user = userRepository.getUserByEmail(email);
            if(user != null){
                Random r = new Random();
                StringBuilder code = new StringBuilder();
                for(int i = 0; i < 4; i++){
                    code.append(r.nextInt(9));
                }
                user.setPassword(String.valueOf(code));
                userRepository.save(user);
                emailService.sendEmail(email, String.valueOf(code));
                return new ResponseEntity<>("The letter was sent", HttpStatus.OK);
            }
            return new ResponseEntity<>("User wasn't found", HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return new ResponseEntity<>("User wasn't found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/check-auth")
    public ResponseEntity<?> checkAuth(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok("true");
    }

}
