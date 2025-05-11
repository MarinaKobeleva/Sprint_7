import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest {
    @Step("Получить список заказов")
    public Response getOrdersList() {
        GetOrdersList getOrdersList = new GetOrdersList();
        return getOrdersList.getOrdersList();
    }

    @Step("Проверить код ответа")
    public void checkResponseCode(Response response, int code) {
        response.then().statusCode(code);
    }

    @Step("Проверить тело ответа c orders.id")
    public void checkResponseBodyWithOrderId(Response response, String key) {
        response.then().assertThat().body(key, notNullValue());
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Get orders and check status code 200")
    @Description("Получить список заказов. Проверить код ответа")
    public void getOrdersAndCheckStatusCode200() {
        Response response = getOrdersList();
        checkResponseCode(response, 200);
    }

    @Test
    @DisplayName("Get orders and check response body")
    @Description("Получить список заказов. Проверить тело ответа")
    public void getOrdersAndCheckResponseBody() {
        Response response = getOrdersList();
        checkResponseBodyWithOrderId(response, "orders.id");
    }

}
