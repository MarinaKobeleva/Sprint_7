import io.restassured.response.Response;
import pojo.CourierWithoutLogin;

import static io.restassured.RestAssured.given;

public class CreateCourierWithoutLogin extends BaseSpecClass {

    private final CourierWithoutLogin courierWithoutLogin;

    public CreateCourierWithoutLogin(CourierWithoutLogin courierWithoutLogin) {
        this.courierWithoutLogin = courierWithoutLogin;
    }

    public Response responseCreateCourierWithoutLogin() {
        Response response = given()
                .spec(requestSpec)
                .body(courierWithoutLogin)
                .when()
                .post(UriConst.COURIER_URI);
        return response;
    }
}
