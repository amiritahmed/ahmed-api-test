package service.validateObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class ValidateObjectResponse {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://collectionapi.metmuseum.org";
    }

    @Test
    public void validateResponsePayload() {

        Response response =
                        given().log().all()
                                .pathParam("objectID", "8")
                        .when()
                                .get("/public/collection/v1/objects/{objectID}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        response.then().time(Matchers.lessThan(3000L));

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("measurements[0].elementName"), "Other");

    }

}
