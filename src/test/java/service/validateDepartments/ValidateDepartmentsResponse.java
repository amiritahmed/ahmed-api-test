package service.validateDepartments;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ValidateDepartmentsResponse {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "https://collectionapi.metmuseum.org";
    }

    @Test
    public void validateResponsePayload() {

        Response response =
                        given().log().ifValidationFails().when().get("/public/collection/v1/departments");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        response.then().time(Matchers.lessThan(3000L));

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getInt("departments[-3].departmentId"), 18);
        List<Integer> actualId = jsonPath.getList("departments.departmentId") ;
        List<Integer> expectedId = Arrays.asList(1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21);

        if(response.statusCode() == 200) {
            Assert.assertTrue(actualId.containsAll(expectedId) && expectedId.containsAll(actualId));
        }
    }
}
