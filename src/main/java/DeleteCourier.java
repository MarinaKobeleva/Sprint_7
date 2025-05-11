import pojo.Courier;
import pojo.GetCourierId;

import static io.restassured.RestAssured.given;

public class DeleteCourier extends BaseSpecClass {

     private final Courier courier;

     public DeleteCourier(Courier courier) {
          this.courier = courier;
     }

     public int getCourierId() {
          return given()
                  .spec(requestSpec)
                  .body(courier)
                  .post(UriConst.LOGIN_URI)
                  .body()
                  .as(GetCourierId.class)
                  .getId();
     }

     public void deleteCourier() {
          given()
                  .spec(requestSpec)
                  .delete(UriConst.DELETE_URI + getCourierId());
     }
}
