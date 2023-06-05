package definitions;

import com.example.connect.ConnectApplication;
import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
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
        requestBody.put("email", "ruby@gmail.com");
        requestBody.put("password", "123456");
        request.header("Content-Type", "application/json");
        return response.jsonPath().getString("message");
    }

    @Given("That an agent is able to register")
    public void thatAnAgentIsAbleToRegister() throws Exception{
        try {

            JSONObject requestBody = new JSONObject();
            requestBody.put("password", "123456");
            requestBody.put("email", "email@mail.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/auth/register/", HttpMethod.POST, request, String.class);

            Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



}
