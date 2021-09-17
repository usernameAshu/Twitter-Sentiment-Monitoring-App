package com.linkedin.api.twilio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Profile("test")
@SpringBootTest
public class TwilioClientTest {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String TWILIO_ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String TWILIO_AUTH_TOKEN;

    @Value("${FROM}")
    private String from;

    @Value("${TO}")
    private String to ;

    String API_TWILIO_SMS_ENDPOINT = "https://api.twilio.com";

    @Autowired
    private WebClient.Builder builder;

    @Test
    public void testSmsApi() throws InterruptedException{
        String API_TWILIO_SMS_PATH = String.format("/2010-04-01/Accounts/%s/Messages.json",TWILIO_ACCOUNT_SID);

        MultiValueMap<String,String> requestBody = new HttpHeaders();
        requestBody.add("Body", "A small test in API, a big leap in Learning!, round 4");
        requestBody.add("From",from);
        requestBody.add("To",to);

        //configure the client
        WebClient client = builder.baseUrl(API_TWILIO_SMS_ENDPOINT)
                .defaultHeaders(headers -> headers.setBasicAuth(TWILIO_ACCOUNT_SID,TWILIO_AUTH_TOKEN))
                .build();

        //sending the request
        client.post()
                .uri(API_TWILIO_SMS_PATH)
                .header("Content-Type","application/x-www-form-urlencoded")
                .bodyValue(requestBody)
                .retrieve()
                .toBodilessEntity()

                .doOnNext( response -> {
                   assertEquals(201,response.getStatusCodeValue());
                });


    }
}
