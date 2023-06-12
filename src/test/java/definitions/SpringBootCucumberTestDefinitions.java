/**
 * This class contains the step definitions for Cucumber tests using Spring Boot and RestAssured.
 */
package definitions;

import com.example.connect.ConnectApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ConnectApplication.class)
public class SpringBootCucumberTestDefinitions {
    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    String port;

    private static Response response;

    /**
     * Retrieves the security key by performing a login request.
     *
     * @return The security key.
     * @throws Exception If an error occurs during the request.
     */
    public String getSecurityKey() throws Exception {
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("emailAddress", "ryan@aol.com");
        requestBody.put("password", "ryan123");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/login/");
        return response.jsonPath().getString("message");
    }

    @Given("That a user is able to register")
    public void thatAUserIsAbleToRegister() throws Exception {
        try {
            // Prepare the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("password", "ryan123");
            requestBody.put("emailAddress", "ryan@aol.com");

            // Set the request headers and body
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

            // Send a POST request to the registration endpoint
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/auth/register/", HttpMethod.POST, request, String.class);

            // Assert the response status code
            Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @When("I login to my account")
    public void iLoginToMyAccount() throws JSONException {
        // Set the base URI and create the request
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("password", "ryan123");
        requestBody.put("emailAddress", "ryan@aol.com");
        request.header("Content-Type", "application/json");

        // Send a POST request to the login endpoint
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/login/");
    }

    @Then("JWT key is returned")
    public void jwtKeyIsReturned() {
        // Assert the response status code and body
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }

    @Given("There are users in the database")
    public void thereAreUsersInTheDatabase() {
        try {
            // Send a GET request to retrieve user data
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/posts/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> properties = JsonPath
                    .from(String.valueOf(response.getBody()))
                    .getList("$");

            // Assert the response status code and that at least one user exists
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assert.assertTrue(properties.size() > 0);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }

    @When("I add a post to my post list")
    public void iAddAPostToMyPostList() throws Exception {
        // Set the base URI and retrieve the security key
        RestAssured.baseURI = BASE_URL;
        String jwtKey = getSecurityKey();
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());
        JSONObject requestBody = new JSONObject();
        requestBody.put("content", "I will conquer the world, just watch me.");
        request.header("Content-Type", "application/json");

        // Send a POST request to add a post
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/posts/");
    }

    @Then("post is added")
    public void thePostIsAdded() {
        // Assert the response status code
        Assert.assertEquals(201, response.getStatusCode());
    }

    @When("I update a post from my post list")
    public void iUpdateAPostFromMyPostList() throws Exception {
        // Set the base URI and retrieve the security key
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + getSecurityKey());
        JSONObject requestBody = new JSONObject();
        requestBody.put("content", "123 ");
        request.header("Content-Type", "application/json");

        // Send a PUT request to update a post
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/posts/1/");
    }

    @Then("the post is updated")
    public void thePostIsUpdated() {
        // Assert the response status code and body
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(response.body());
    }
}
