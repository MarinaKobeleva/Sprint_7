import java.io.File;

import static io.restassured.RestAssured.given;

public class DeleteCourier extends CourierId {

     public int getCourierId() {
          return given()
                  .header("Content-type", "application/json")
                  .body(new File("src/test/resources/checkCourierId.json"))
                  .post("/api/v1/courier/login")
                  .body()
                  .as(CourierId.class)
                  .getId();
     }

     public void deleteCourier() {
          given()
                  .delete("/api/v1/courier/" + getCourierId());
     }
}
