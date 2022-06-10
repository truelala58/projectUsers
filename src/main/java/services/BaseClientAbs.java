package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public  abstract  class BaseClientAbs {
    protected String resourcesFolderPath = System.getProperty("user.dir");

    protected RequestSpecification requestSpecification = RestAssured
            .given()
            .contentType(ContentType.JSON)
        //  .baseUri(System.getProperty("base.url"))  //URI не хочет браться из buil.gradle, ошибка: IllegalArgumentException: Base URI cannot be null
            .baseUri("https://petstore.swagger.io/v2")
            .log()
            .all();

    protected ResponseSpecification responseSpecification = requestSpecification
            .then()
            .statusCode(200)
            .log()
            .all();

}
