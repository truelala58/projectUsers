package data.users;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String email;
    private String firstName;
    private long id;
    private String lastName;
    private String password;
    private String phone;
    private long userStatus;
    private String username;

}
