import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseSpecClass {
    protected static RequestSpecification requestSpec;

    static {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru")
                .setContentType("application/json")
                .build();
    }
}
