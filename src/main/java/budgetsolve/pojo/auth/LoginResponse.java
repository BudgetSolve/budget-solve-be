package budgetsolve.pojo.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginResponse {

    String token;

    public LoginResponse(String jwt, String username, String email) {
        this.token = jwt;
    }
}
