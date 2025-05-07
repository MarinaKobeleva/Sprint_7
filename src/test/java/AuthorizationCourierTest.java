import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorizationCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        File newCourier = new File("src/test/resources/newCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier");
    }

    @After
    public void deleteCourierAfterTest() {
        DeleteCourier deleteCourier = new DeleteCourier();
        deleteCourier.deleteCourier();
    }

    @Step("Авторизация со всеми обязательными полями")
    public Response authorizationWithRequiredFields() {
        File newCourierWithRequiredFields = new File("src/test/resources/newCourierWithRequiredFields.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourierWithRequiredFields)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Авторизация без поля логин")
    public Response authorizationWithoutLoginFields() {
        File newCourierWithoutLogin = new File("src/test/resources/newCourierWithoutLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourierWithoutLogin)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Авторизация под неправильным логином")
    public Response authorizationIncorrectLogin() {
        File incorrectLogin = new File("src/test/resources/incorrectLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(incorrectLogin)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Авторизация под неправильным паролем")
    public Response authorizationIncorrectPassword() {
        File incorrectPassword = new File("src/test/resources/incorrectPassword.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(incorrectPassword)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Авторизация под несуществующим пользователем")
    public Response authorizationIncorrectLoginAndPassword() {
        File incorrectLoginAndPassword = new File("src/test/resources/incorrectLoginAndPassword.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(incorrectLoginAndPassword)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Проверить тело ответа с String")
    public void checkResponseBodyWithString(Response response, String key, String value) {
        response.then().body(key, equalTo(value));
    }

    @Step("Проверить тело ответа c id")
    public void checkResponseBodyWithId(Response response, String key) {
        response.then().assertThat().body(key, notNullValue());
        System.out.println(response.body().asString());
    }

    @Step("Проверить код ответа")
    public void checkResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }


    @Test
    @DisplayName("Authorization and check status code 200")
    @Description("Авторизация со всеми обязательными полями. Проверить код ответа")
    public void authorizationAndCheckStatusCode200() {
        Response response = authorizationWithRequiredFields();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Authorization and check response body")
    @Description("Авторизация со всеми обязательными полями. Проверить тело ответа")
    public void authorizationAndCheckResponseBody() {
        Response response = authorizationWithRequiredFields();
        checkResponseBodyWithId(response, "id");
    }

    @Test
    @DisplayName("Authorization with incorrect login and check status code 404")
    @Description("Авторизация под неправильным логином. Проверить код ответа")
    public void authorizationWithIncorrectLoginAndCheckStatusCode404() {
        Response response = authorizationIncorrectLogin();
        checkResponseCode(response, 404);
    }

    @Test
    @DisplayName("Authorization with incorrect login and check response body")
    @Description("Авторизация под неправильным логином. Проверить тело ответа")
    public void authorizationWithIncorrectLoginAndCheckResponseBody() {
        Response response = authorizationIncorrectLogin();
        checkResponseBodyWithString(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Authorization with incorrect password and check status code 404")
    @Description("Авторизация под неправильным паролем. Проверить код ответа")
    public void authorizationWithIncorrectPasswordAndCheckStatusCode404() {
        Response response = authorizationIncorrectPassword();
        checkResponseCode(response, 404);
    }

    @Test
    @DisplayName("Authorization with incorrect password and check response body")
    @Description("Авторизация под неправильным паролем. Проверить тело ответа")
    public void authorizationWithIncorrectPasswordAndCheckResponseBody() {
        Response response = authorizationIncorrectPassword();
        checkResponseBodyWithString(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Authorization without login and check status code 400")
    @Description("Авторизация без логина. Проверить код ответа")
    public void authorizationWithoutLoginAndCheckStatusCode400() {
        Response response = authorizationWithoutLoginFields();
        checkResponseCode(response, 400);
    }

    @Test
    @DisplayName("Authorization without password and check response body")
    @Description("Авторизация без логина. Проверить тело ответа")
    public void authorizationWithoutLoginAndCheckResponseBody() {
        Response response = authorizationWithoutLoginFields();
        checkResponseBodyWithString(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Authorization with incorrect login and password and check status code 404")
    @Description("Авторизация под неправильным логином и паролем. Проверить код ответа")
    public void authorizationWithIncorrectLoginAndPasswordAndCheckStatusCode404() {
        Response response = authorizationIncorrectLoginAndPassword();
        checkResponseCode(response, 404);
    }

    @Test
    @DisplayName("Authorization with incorrect password and check response body")
    @Description("Авторизация под неправильным логином и паролем. Проверить тело ответа")
    public void authorizationWithIncorrectLoginAndPasswordAndCheckResponseBody() {
        Response response = authorizationIncorrectLoginAndPassword();
        checkResponseBodyWithString(response, "message", "Учетная запись не найдена");
    }
}
