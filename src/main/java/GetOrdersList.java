import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetOrdersList extends BaseSpecClass{

    public GetOrdersList() {
    }

    public Response getOrdersList() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(UriConst.ORDERS_URI);
        return response;
    }
}
