package com.example.paypalms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.ZonedDateTime;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableAsync
public class PaypalMsApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(PaypalMsApplication.class, args);


 /*       try {
            String url = "https://api.sandbox.paypal.com/v1/payments/payouts";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("authorization", "Bearer A21AAKLqdjzgvddsOExdUrgYviUiyS4Q7Xyak-tDoe1t0EbrhOWWjFjTavQvSeM3ybz2qZiBwGBh7Uvp1-J_jtjwU2gxHdvmA");
            con.setRequestProperty("content-type", "application/json");
            con.setRequestProperty("paypal-mock-response", "{\"mock_application_codes\":\"INSUFFICIENT_FUNDS\"}");
            String body = "{" +
                    "  \"sender_batch_header\": {" +
                    "    \"sender_batch_id\": \"1516149278437\"," +
                    "    \"email_subject\": \"This email is for alternate scenario - INSUFFICIENT_FUNDS\"" +
                    "  }," +
                    "  \"items\": [" +
                    "    {" +
                    "      \"recipient_type\": \"EMAIL\"," +
                    "      \"receiver\": \"payouts-simulator-receiver@paypal.com\"," +
                    "      \"note\": \"INSUFFICIENT_FUNDS\"," +
                    "      \"sender_item_id\": \"15161492784370\"," +
                    "      \"amount\": {" +
                    "        \"currency\": \"USD\"," +
                    "        \"value\": \"1.00\"" +
                    "      }" +
                    "    }" +
                    "  ]" +
                    "}";

            // Send request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/").resourceChain(false);
    }

}
