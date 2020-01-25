package petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {
    int id = 12345;
    String jsonData = "{\"id\":" + this.id + ",\"category\":{\"id\":1,\"name\":\"domestic\"},\"name\":\"GoodDoggo\",\"photoUrls\":[\"urlOfDogPic\"],\"tags\":[{\"id\":1,\"name\":\"tag1\"}],\"status\":\"available\"}";

    @Test
    public void task1CreatePet(){
        System.out.println("CreatePet executed");

        RestAssured.baseURI= "https://petstore.swagger.io";

        given().contentType(ContentType.JSON).body(jsonData).when().post("/v2/pet").then().assertThat().statusCode(200);
    }

    @Test
    public void task2VerifyData(){
        System.out.println("Verify Pet Created with Correct Data");
        RestAssured.baseURI= "https://petstore.swagger.io";
        Response response;
        response = RestAssured.given().urlEncodingEnabled(true)
                .get("/v2/pet/" + this.id);
        Assert.assertTrue(response.asString().equals(jsonData),"Response not matching with data used to create Pet");
    }

    @Test
    public void task3UpdatePet(){
        System.out.println("UpdatePet Executed");

        RestAssured.baseURI= "https://petstore.swagger.io";

        given().urlEncodingEnabled(true)
                .param("name","VeryGoodDoggo")
                .post("/v2/pet/" + this.id)
                .then().assertThat().statusCode(200);
    }

    @Test
    public void task4DeletePet(){
        System.out.println("DeletePet Executed");

        RestAssured.baseURI= "https://petstore.swagger.io";

        given().urlEncodingEnabled(true)
                .delete("/v2/pet/" + this.id).then().assertThat().statusCode(200);
    }
}