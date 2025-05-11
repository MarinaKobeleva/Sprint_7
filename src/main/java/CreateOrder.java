import io.restassured.response.Response;
import pojo.Order;

import static io.restassured.RestAssured.given;

public class CreateOrder extends BaseSpecClass {

    private final Order order;

    public CreateOrder(Order order) {
        this.order = order;
    }

    public Response createOrder() {
        Response response = given()
                .spec(requestSpec)
                .body(order)
                .when()
                .post(UriConst.ORDERS_URI);
        return response;
    }
}
