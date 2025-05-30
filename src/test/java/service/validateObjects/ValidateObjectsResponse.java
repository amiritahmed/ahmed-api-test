package service.validateObjects;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class ValidateObjectsResponse {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://collectionapi.metmuseum.org";
    }

    @Test
    public void validateStatusCode() {

        Response response =
                        given().log().all()
                                .queryParam("metadataDate", "2020-01-01")
                                .queryParam("departmentIds", 3)
                        .when()
                                .get("/public/collection/v1/objects");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        response.then().time(Matchers.lessThan(3000L));
        response.then().assertThat().body(Matchers.containsString("\"total\""));
        response.then().assertThat().body(Matchers.containsString("\"objectIDs\""));
    }

    @Test
    public void validateResponseDepartmentID3() {

        Response response =
                given().log().all()
                        .queryParam("metadataDate", "2020-01-01")
                        .queryParam("departmentIds", 3)
                        .when()
                        .get("/public/collection/v1/objects");

        response.prettyPrint();
        response.path("total", "6305");
        Assert.assertFalse(response.body().jsonPath().getList("objectIDs").isEmpty());
    }
}
