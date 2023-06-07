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

    public String getSecurityKey() throws Exception{
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "jack@aol.com");
        requestBody.put("password", "jack123");
        request.header("Content-Type", "application/json");
        return response.jsonPath().getString("message");
    }

    @Given("That a user is able to register")
    public void thatAUserIsAbleToRegister() throws Exception{
        try {

            JSONObject requestBody = new JSONObject();
            requestBody.put("password", "jack123");
            requestBody.put("emailAddress", "jack@aol.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/auth/users/register/", HttpMethod.POST, request, String.class);

            Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Given("A list of properties are available")
    public void aListOfPostsAreAvailable() {
        try {
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/users/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> properties = JsonPath
                    .from(String.valueOf(response
                            .getBody()))
                    .getList("$");
            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assert.assertTrue(properties.size() > 0);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }
//
//    @When("I login to my account")
//    public void iLoginToMyAccount() throws JSONException {
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification request = RestAssured.given();
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("password", "jack123");
//        requestBody.put("email", "jack@aol.com");
//        request.header("Content-Type", "application/json");
//        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/login/");
//    }
//
//    @Then("JWT key is returned")
//    public void jwtKeyIsReturned() {
//        Assert.assertEquals(200, response.getStatusCode());
//        Assert.assertNotNull(response.body());
//    }






}
