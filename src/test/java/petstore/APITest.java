package petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {
    private static final int ID = 12345;
    private  static final String BASE_URI = "https://petstore.swagger.io";
    String jsonData = "{\"id\":" + ID + ",\"category\":{\"id\":1,\"name\":\"domestic\"},\"name\":\"GoodDoggo\",\"photoUrls\":[\"urlOfDogPic\"],\"tags\":[{\"id\":1,\"name\":\"tag1\"}],\"status\":\"available\"}";

    @Test
    public void task1CreatePet(){
        /* Creating new Pet entry using Post request
        *  hardcoded body data from defined variable "jasonData" is used to create new entry */
        RestAssured.baseURI= BASE_URI;
        given().contentType(ContentType.JSON).body(jsonData).when().post("/v2/pet").then().assertThat().statusCode(200);
        /* Asserted StatusCode = 200 to verify Pet entry is created */
        System.out.println("CreatePet executed");
    }

    @Test
    public void task2VerifyData(){
        /* Verify data of the Pet which was created
        *  Using Get method to search Pet using ID and
        *  comparing data with "jasonData" variable used to create pet */
        RestAssured.baseURI=  BASE_URI;
        Response response;
        response = RestAssured.given().urlEncodingEnabled(true)
                .get("/v2/pet/" + ID);
        /* Assert if Response data equals to data used to create Pet */
        Assert.assertTrue(response.asString().equals(jsonData),"Response not matching with data used to create Pet");
        System.out.println("Verify Pet Created with Correct Data");
    }

    @Test
    public void task3UpdatePet(){
        /* Updating a Pet_Name in store using form data - Post request */
        RestAssured.baseURI=  BASE_URI;
        given().urlEncodingEnabled(true)
                .param("name","VeryGoodDoggo")
                .post("/v2/pet/" + ID)
                .then().assertThat().statusCode(200); // Asserting status code to verify Update
        System.out.println("UpdatePet Executed");
    }

    @Test
    public void task4DeletePet(){
        /* Deleting the Pet which was created and updated in earlier test cases */
        RestAssured.baseURI=  BASE_URI;
        given().urlEncodingEnabled(true)
                .delete("/v2/pet/" + ID)
                .then().assertThat().statusCode(200); // Asserting status code to verify record Deleted
        System.out.println("DeletePet Executed");
    }
}