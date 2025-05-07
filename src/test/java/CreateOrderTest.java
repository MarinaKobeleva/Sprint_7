import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String pathName;

    public CreateOrderTest(String pathName) {
        this.pathName = pathName;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters
    public static Object[][] getFile() {
        return new Object[][]{
                {"src/test/resources/blackColor.json"},
                {"src/test/resources/greyColor.json"},
                {"src/test/resources/blackAndGreyColor.json"},
                {"src/test/resources/withoutColor.json"},
        };
    }

    @Step("Создать заказ")
    public Response createOrder() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new File(pathName))
                .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step("Проверить код ответа")
    public void checkResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }

    @Step("Проверить тело ответа c track")
    public void checkResponseBodyWithTrack(Response response, String key) {
        response.then().assertThat().body(key, notNullValue());
        System.out.println(response.body().asString());
    }


    @Test
    @DisplayName("Create order and check status code 201")
    @Description("Создать заказ. Проверить код ответа")
    public void createOrderAndCheckStatusCode201() {
        Response response = createOrder();
        checkResponseCode(response, 201);
    }

    @Test
    @DisplayName("Create order and check response body")
    @Description("Создать заказ. Проверить тело ответа")
    public void createOrderAndCheckResponseBody() {
        Response response = createOrder();
        checkResponseBodyWithTrack(response, "track");
    }
}
