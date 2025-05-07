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

public class CreateCourierTest {


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @After
    public void deleteCourierAfterTest() {
        DeleteCourier deleteCourier = new DeleteCourier();
        deleteCourier.deleteCourier();
    }

    @Step("Создать нового курьера")
    public Response createNewCourier() {
        File newCourier = new File("src/test/resources/newCourier.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Создать нового курьера со всеми обязательными полями")
    public Response createNewCourierWithRequiredFields() {
        File newCourierWithRequiredFields = new File("src/test/resources/newCourierWithRequiredFields.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourierWithRequiredFields)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Создать нового курьера без поля логин")
    public Response createNewCourierWithoutLoginFields() {
        File newCourierWithoutLogin = new File("src/test/resources/newCourierWithoutLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourierWithoutLogin)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Создать нового курьера без поля пароль")
    public Response createNewCourierWithoutPasswordFields() {
        File newCourierWithoutPassword = new File("src/test/resources/newCourierWithoutPassword.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourierWithoutPassword)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Создать нового курьера с существующим логином")
    public Response createNewCourierWithIdenticalLogin() {
        File newCourierWithIdenticalLogin = new File("src/test/resources/newCourierWithIdenticalLogin.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourierWithIdenticalLogin)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Проверить тело ответа с String")
    public void checkResponseBodyWithString(Response response, String key, String value) {
        response.then().body(key, equalTo(value));
    }

    @Step("Проверить тело ответа с boolean")
    public void checkResponseBodyWithBoolean(Response response, String key, boolean value) {
        response.then().body(key, equalTo(value));
    }

    @Step("Проверить код ответа")
    public void checkResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }


    @Test
    @DisplayName("Create new courier and check status code 201")
    @Description("курьера можно создать. Проверить код ответа")
    public void createNewCourierAndCheckStatusCode201() {
        Response response = createNewCourier();
        checkResponseCode(response, 201);
    }

    @Test
    @DisplayName("Create new courier and check response body")
    @Description("курьера можно создать. Проверить тело ответа")
    public void createNewCourierAndCheckResponseBody() {
        Response response = createNewCourier();
        checkResponseBodyWithBoolean(response, "ok", true);
    }

    @Test
    @DisplayName("Create identical courier and check status code 409")
    @Description("нельзя создать двух одинаковых курьеров. Проверить код ответа")
    public void createIdenticalCourierAndCheckStatusCode409() {
        Response response1 = createNewCourier();
        Response response2 = createNewCourier();
        checkResponseCode(response2, 409);
    }

    @Test
    @DisplayName("Create identical courier and check response body")
    @Description("нельзя создать двух одинаковых курьеров. Проверить тело ответа")
    public void createIdenticalCourierAndCheckResponseBody() {
        Response response1 = createNewCourier();
        Response response2 = createNewCourier();
        checkResponseBodyWithString(response2, "message", "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Create new courier with required fields and check status code 201")
    @Description("чтобы создать курьера, нужно передать в ручку все обязательные поля. Проверить код ответа")
    public void createNewCourierWithRequiredFieldsAndCheckStatusCode201() {
        Response response = createNewCourierWithRequiredFields();
        checkResponseCode(response, 201);
    }

    @Test
    @DisplayName("Create new courier with required fields and check response body")
    @Description("чтобы создать курьера, нужно передать в ручку все обязательные поля. Проверить тело ответа")
    public void createNewCourierWithRequiredFieldsAndCheckResponseBody() {
        Response response = createNewCourierWithRequiredFields();
        checkResponseBodyWithBoolean(response, "ok", true);
    }

    @Test
    @DisplayName("Create new courier without login and check status code 400")
    @Description("если одного из полей нет (логина), запрос возвращает ошибку. Проверить код ответа")
    public void createNewCourierWithoutLoginAndCheckStatusCode400() {
        Response responseForDelete = createNewCourier();
        Response response = createNewCourierWithoutLoginFields();
        checkResponseCode(response, 400);
    }

    @Test
    @DisplayName("Create new courier without login and check response body")
    @Description("если одного из полей нет (логина), запрос возвращает ошибку. Проверить тело ответа")
    public void createNewCourierWithoutLoginAndCheckResponseBody() {
        Response responseForDelete = createNewCourier();
        Response response = createNewCourierWithoutLoginFields();
        checkResponseBodyWithString(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Create new courier without password and check status code 400")
    @Description("если одного из полей нет (пароля), запрос возвращает ошибку. Проверить код ответа")
    public void createNewCourierWithoutPasswordAndCheckStatusCode400() {
        Response responseForDelete = createNewCourier();
        Response response = createNewCourierWithoutPasswordFields();
        checkResponseCode(response, 400);
    }

    @Test
    @DisplayName("Create new courier without password and check response body")
    @Description("если одного из полей нет (пароля), запрос возвращает ошибку. Проверить тело ответа")
    public void createNewCourierWithoutPasswordAndCheckResponseBody() {
        Response responseForDelete = createNewCourier();
        Response response = createNewCourierWithoutPasswordFields();
        checkResponseBodyWithString(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Create new courier with identical login and check status code 409")
    @Description("если создать пользователя с логином, который уже есть, возвращается ошибка. Проверить код ответа")
    public void createNewCourierWithIdenticalLoginAndCheckStatusCode409() {
        Response responseFirstCourier = createNewCourier();
        Response response = createNewCourierWithIdenticalLogin();
        checkResponseCode(response, 409);
    }

    @Test
    @DisplayName("Create new courier with identical login and check response body")
    @Description("если создать пользователя с логином, который уже есть, возвращается ошибка. Проверить тело ответа")
    public void createNewCourierWithIdenticalLoginAndCheckResponseBody() {
        Response responseFirstCourier = createNewCourier();
        Response response = createNewCourierWithIdenticalLogin();
        checkResponseBodyWithString(response, "message", "Этот логин уже используется. Попробуйте другой.");
    }
}
