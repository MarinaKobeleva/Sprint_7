import io.restassured.response.Response;
import pojo.Courier;

import static io.restassured.RestAssured.given;

public class Authorization extends BaseSpecClass{

    private final Courier courier;

    public Authorization(Courier courier) {
        this.courier = courier;
    }

    public Response authorization() {
        Response response = given()
                .spec(requestSpec)
                .body(courier)
                .when()
                .post(UriConst.LOGIN_URI);
        return response;
    }
}
