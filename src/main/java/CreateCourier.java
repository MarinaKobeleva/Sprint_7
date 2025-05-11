import io.restassured.response.Response;
import pojo.Courier;

import static io.restassured.RestAssured.given;

public class CreateCourier extends BaseSpecClass {

    private final Courier courier;

    public CreateCourier(Courier courier) {
        this.courier = courier;
    }

    public Response createCourier() {
        Response response = given()
                .spec(requestSpec)
                .body(courier)
                .when()
                .post(UriConst.COURIER_URI);
        return response;
    }
}
