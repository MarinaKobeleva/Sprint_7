import pojo.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CourierWithoutLogin;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorizationCourierTest {

    @Before
    public void setUp() {
        Courier courier = new Courier("tralala", "8965", "one");
        CreateCourier createCourier = new CreateCourier(courier);
        createCourier.createCourier();
    }

    @After
    public void deleteCourierAfterTest() {
        Courier courier = new Courier("tralala", "8965");
        DeleteCourier deleteCourier = new DeleteCourier(courier);
        deleteCourier.deleteCourier();
    }

    @Step("Авторизация со всеми обязательными полями")
    public Response authorizationWithRequiredFields() {
        Courier courier = new Courier("tralala", "8965");
        Authorization authorization = new Authorization(courier);
        return authorization.authorization();
    }

    @Step("Авторизация без поля логин")
    public Response authorizationWithoutLoginFields() {
            CourierWithoutLogin courierWithoutLogin = new CourierWithoutLogin("8965");
            AuthorizationWithoutLogin authorizationWithoutLogin = new AuthorizationWithoutLogin(courierWithoutLogin);
            return authorizationWithoutLogin.authorizationWithoutLogin();
        }

    @Step("Авторизация под неправильным логином")
    public Response authorizationIncorrectLogin() {
        Courier courier = new Courier("tralalala", "8965");
        Authorization authorization = new Authorization(courier);
        return authorization.authorization();
    }

    @Step("Авторизация под неправильным паролем")
    public Response authorizationIncorrectPassword() {
        Courier courier = new Courier("tralala", "6565");
        Authorization authorization = new Authorization(courier);
        return authorization.authorization();
    }

    @Step("Авторизация под несуществующим пользователем")
    public Response authorizationIncorrectLoginAndPassword() {
        Courier courier = new Courier("tralalala", "6565");
        Authorization authorization = new Authorization(courier);
        return authorization.authorization();
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
