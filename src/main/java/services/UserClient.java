package services;

import com.github.javafaker.Faker;
import data.users.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClientAbs {

    public UserClient(){
        this.requestSpecification.basePath("/user");
    }

    public String createUser(User user){
         String createResp = given()
                .spec(this.requestSpecification)
                .when()
                .body(user)
                .post("/")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                                 this.resourcesFolderPath, "user_response.json"))))
                .extract().body().jsonPath().getString(".");
         return createResp;
    }
    public String loginUser(String username, String password){
          String loginResp = given()
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
                .extract().body().jsonPath().getString(".");
          return loginResp;
    }

    public String logoutUser(){
     String logoutResp = given()
                .spec(this.requestSpecification)
                .when()
                .get("/logout")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                        .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                                this.resourcesFolderPath, "user_response.json"))))
             .extract().body().jsonPath().getString(".");
     return logoutResp;
    }

    public String deleteUser(String username){
        String deleteResp = given()
                .spec(this.requestSpecification)
                .when()
                .delete("/"+ username)
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                       this.resourcesFolderPath, "user_response.json"))))
                .extract().body().jsonPath().getString(".");
        return deleteResp;
    }

    public String deleteUserNotFound(String username){
      String deleteRespError =  given()
                .spec(this.requestSpecification)
                .when()
                .delete("/"+ username)
                .then()
                .assertThat()
                .statusCode(404)
                .extract().body().asString();
      return  deleteRespError;
    }

    public String updateUser(User user, String username){
        String updatedUser =  given()
                .spec(this.requestSpecification)
                .when()
                .body(user)
                .put("/"+ username)
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .extract().body().jsonPath().getString(".");
        return updatedUser;
    }

    public User getUser(String username){
        Response response =   given()
                .spec(this.requestSpecification)
                .when()
                .get("/"+ username);

        User user = response.as(User.class);
        response
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "getUser_response.json"))));
        return user;
    }

    public String createUserList(List<User> userList){
        String userCreateListResp = given()
                .spec(this.requestSpecification)
                .when()
                .body(userList)
                .post("/createWithList")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .extract().body().jsonPath().getString(".");
        return userCreateListResp;
    }

    public String createUserArray(User userArray[]){
        String userCreateListResp = given()
                .spec(this.requestSpecification)
                .when()
                .body(userArray)
                .post("/createWithArray")
                .then()
                .assertThat()
                .spec(this.responseSpecification)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(String.format("%s/src/main/resources/%s",
                        this.resourcesFolderPath, "user_response.json"))))
                .extract().body().jsonPath().getString(".");
        return userCreateListResp;
    }
}
