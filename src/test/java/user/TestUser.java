package user;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import data.users.User;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import services.UserClient;
import services.UserModule;
import java.util.ArrayList;
import java.util.List;

@Guice(modules = UserModule.class)
public class TestUser {

    @Inject
    private UserClient userClient;

    Faker faker = new Faker();

    @Test
    public void createLoginLogoutUserTest(){
        long id = (long) (Math.random()*99999);
        User newUser = User.builder()
                .id(id)
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();

        userClient.createUser(newUser);
        userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        userClient.logoutUser();
    }

    @Test
    public void createLoginDeleteUserTest(){
        long id = (long) (Math.random()*99999);
        String username = faker.name().username();
        User newUser = User.builder()
                .id(id)
                .username(username)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();

        userClient.createUser(newUser);
        userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        userClient.deleteUser(username);
    }

    @Test
    public void createLoginDeleteUserNotFoundTest(){
        long id = (long) (Math.random()*99999);
        String username = faker.name().username();
        User newUser = User.builder()
                .id(id)
                .username(username)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();

        userClient.createUser(newUser);
        userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        userClient.deleteUserNotFound(faker.name().username());
    }

    @Test
    public void createLoginUpdateUserTest(){
        long id = (long) (Math.random()*99999);
        String username = faker.name().username();
        User newUser = User.builder()
                .id(id)
                .username(username)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();

        userClient.createUser(newUser);
        userClient.loginUser(newUser.getUsername(),newUser.getPassword());

        User updateUser = User.builder()
                .id(id)
                .username(username)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();

        userClient.updateUser(updateUser,username);
        userClient.getUser(username);

    }
    @Test
    public void createLoginGetUserTest(){
        long id = (long) (Math.random()*99999);
        String username = faker.name().username();
        User newUser = User.builder()
                .id(id)
                .username(username)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();

        userClient.createUser(newUser);
        userClient.getUser(username);
    }
    @Test
    public void createListGetUserTest(){
        String firstUsername = faker.name().username();
        User firstUser = User.builder()
                .id((long) (Math.random()*99999))
                .username(firstUsername)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();
        String secondUsername = faker.name().username();
        User secondUser = User.builder()
                .id((long) (Math.random()*99999))
                .username(secondUsername)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(String.format("%s@mail.ru",faker.name().firstName()+faker.name().lastName()))
                .password(faker.code().ean8())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();
        List<User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);

        userClient.createUserList(userList);
        userClient.getUser(firstUsername);
        userClient.getUser(secondUsername);
    }

}
