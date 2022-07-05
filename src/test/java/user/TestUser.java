package user;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import data.users.User;
import org.testng.Assert;
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
    @Inject
    private Faker faker;

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

        String createResp = userClient.createUser(newUser);
        Assert.assertTrue(createResp.contains(Long.toString(newUser.getId())));
        String loginResp = userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        Assert.assertTrue(loginResp.contains("logged in user session:"));
        String logoutResp = userClient.logoutUser();
        Assert.assertTrue(logoutResp.contains("message:ok"));
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

        String createResp = userClient.createUser(newUser);
        Assert.assertTrue(createResp.contains(Long.toString(newUser.getId())));
        String loginResp = userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        Assert.assertTrue(loginResp.contains("logged in user session:"));
        String deletedResp = userClient.deleteUser(username);
        Assert.assertTrue(deletedResp.contains(newUser.getUsername()));
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

        String createResp = userClient.createUser(newUser);
        Assert.assertTrue(createResp.contains(Long.toString(newUser.getId())));
        String loginResp = userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        Assert.assertTrue(loginResp.contains("logged in user session:"));
        String deleteRespError = userClient.deleteUserNotFound(faker.name().username());
        Assert.assertTrue(deleteRespError.isEmpty());

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

        String createResp = userClient.createUser(newUser);
        Assert.assertTrue(createResp.contains(Long.toString(newUser.getId())));
        String loginResp = userClient.loginUser(newUser.getUsername(),newUser.getPassword());
        Assert.assertTrue(loginResp.contains("logged in user session:"));

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

        String updatedUserResp = userClient.updateUser(updateUser,username);
        Assert.assertTrue(updatedUserResp.contains(Long.toString(updateUser.getId())));
        User getUser =  userClient.getUser(username);
        checkGetUser(updateUser,getUser);
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

        String createResp = userClient.createUser(newUser);
        Assert.assertTrue(createResp.contains(Long.toString(newUser.getId())));
        User getUser =  userClient.getUser(username);
        checkGetUser(newUser,getUser);
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

        String userCreateListResp = userClient.createUserList(userList);
        Assert.assertTrue(userCreateListResp.contains("message:ok"));
        User getUserFirst = userClient.getUser(firstUsername);
        checkGetUser(firstUser,getUserFirst);
        User getUserSecond = userClient.getUser(secondUsername);
        checkGetUser(secondUser,getUserSecond);
    }
    public void checkGetUser(User user, User getUser){
        Assert.assertEquals(user.getUsername(), getUser.getUsername());
        Assert.assertEquals(user.getEmail(), getUser.getEmail());
        Assert.assertEquals(user.getFirstName(), getUser.getFirstName());
        Assert.assertEquals(user.getLastName(), getUser.getLastName());
        Assert.assertEquals(user.getPassword(), getUser.getPassword());
        Assert.assertEquals(user.getPhone(), getUser.getPhone());
        Assert.assertEquals(user.getUserStatus(), getUser.getUserStatus());
        Assert.assertEquals(user.getId(), getUser.getId());
    }

}
