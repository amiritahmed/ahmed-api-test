package service.validateSearch;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class ValidateSearchResponse {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://collectionapi.metmuseum.org";
    }

    @Test
    public void validateResponsePayload() {

        Response response =
                        given().log().all()
                                .queryParam("title", true)
                                .queryParam("isHighlight", true)
                                .queryParam("q", true)
                                .queryParam("title", true)
                                .queryParam("tags", true)
                                .queryParam("departmentId", 5)
                                .queryParam("isOnView", 5)
                                .queryParam("medium", 5)
                                .queryParam("artistOrCulture", 5)
                        .when()
                                .get("/public/collection/v1/search");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        response.then().time(Matchers.lessThan(3000L));

        JsonPath jsonPath = response.jsonPath();
      // Assert.assertEquals(jsonPath.getString("measurements[0].elementName"), "Other");

    }

}
