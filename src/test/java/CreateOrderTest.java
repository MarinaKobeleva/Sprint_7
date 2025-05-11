import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Order;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{""}},
        };
    }

    @Step("Создать заказ")
    public Response createOrder() {
        Order order = new Order("Кукушка", "Кукушкина", "Садовая, 56", "Садовая", "+7 800 355 35 35", 6, "2026-06-06", "Очень жду", color);
        CreateOrder createOrder = new CreateOrder(order);
        return createOrder.createOrder();
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
