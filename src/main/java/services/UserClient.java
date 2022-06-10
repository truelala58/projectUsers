package services;

import com.github.javafaker.Faker;
import data.users.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClientAbs {

    public UserClient(){
        this.requestSpecification.basePath("/user");
    }

//    public User newUser(long id, String username, String firstName, String lastName, String email, String password, String phone, long userStatus){
//        User newUser = User.builder()
//                .id(id)
//                .username(username)
//                .firstName(firstName)
//                .lastName(lastName)
//                .email(email)
//                .password(password)
//                .phone(phone)
//                .userStatus(userStatus)
//                .build();
//        return newUser;
//    }

    public void createUser(User user){
                 given()
                .spec(this.requestSpecification)
                .when()
                .body(user)
                .post("/")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                                 this.resourcesFolderPath, "user_response.json"))));
    }
    public void loginUser(String username, String password){
                 given()
                .spec(this.requestSpecification)
                .when()
                .queryParam("username",username)
                .queryParam("password",password)
                .get("/login")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .body(Matchers.containsStringIgnoringCase("logged in user session:"));
    }
    public void logoutUser(){
                given()
                .spec(this.requestSpecification)
                .when()
                .get("/logout")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                        .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                                this.resourcesFolderPath, "user_response.json"))))
                .body(Matchers.containsStringIgnoringCase("message\":\"ok"));
    }
    public void deleteUser(String username){
                 given()
                .spec(this.requestSpecification)
                .when()
                .delete("/"+ username)
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                       this.resourcesFolderPath, "user_response.json"))))
               .body(Matchers.containsStringIgnoringCase("message\":\"" + username));
    }
    public void deleteUserNotFound(String username){
        given()
                .spec(this.requestSpecification)
                .when()
                .delete("/"+ username)
                .then()
                .assertThat()
                .statusCode(404);
    }
    public void updateUser(User user, String username){
                given()
                .spec(this.requestSpecification)
                .when()
                .body(user)
                .put("/"+ username)
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .body(Matchers.containsStringIgnoringCase("message\":\"" + user.getId()));
    }

    public void getUser(String username){
                 given()
                .spec(this.requestSpecification)
                .when()
                .get("/"+ username)
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "getUser_response.json"))));
    }
    public void createUserList(List<User> userList){
                given()
                .spec(this.requestSpecification)
                .when()
                .body(userList)
                .post("/createWithList")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .body(Matchers.containsStringIgnoringCase("message\":\"ok"));
    }
    public void createUserArray(User userArray[]){
                 given()
                .spec(this.requestSpecification)
                .when()
                .body(userArray)
                .post("/createWithArray")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .body(Matchers.containsStringIgnoringCase("message\":\"ok"));
    }
}
