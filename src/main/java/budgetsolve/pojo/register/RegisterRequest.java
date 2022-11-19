package budgetsolve.pojo.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RegisterRequest {

    String firstName;

    String lastName;

    String email;

    String password;

}
