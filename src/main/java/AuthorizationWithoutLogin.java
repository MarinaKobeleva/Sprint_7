import io.restassured.response.Response;
import pojo.CourierWithoutLogin;

import static io.restassured.RestAssured.given;

public class AuthorizationWithoutLogin extends BaseSpecClass{

    private final CourierWithoutLogin courierWithoutLogin;

    public AuthorizationWithoutLogin(CourierWithoutLogin courierWithoutLogin) {
        this.courierWithoutLogin = courierWithoutLogin;
    }

    public Response authorizationWithoutLogin() {
        Response response = given()
                .spec(requestSpec)
                .body(courierWithoutLogin)
                .when()
                .post(UriConst.LOGIN_URI);
        return response;
    }
}
