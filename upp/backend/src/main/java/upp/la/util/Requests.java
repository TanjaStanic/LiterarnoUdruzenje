package upp.la.util;

import upp.la.model.EmailTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Requests {

  public static void sendEmail(EmailTemplate emailTemplate) throws IOException {

    URL url = new URL("https://hsejfoit96.execute-api.us-east-1.amazonaws.com/dev/email");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json; utf-8");
    con.setRequestProperty("Accept", "application/json");
    con.setDoOutput(true);

    String jsonInputString =
        String.format(
            "{\"address\": \"%s\",\"subject\": \"%s\",\"message\": \"%s\"}",
            emailTemplate.getAddress(), emailTemplate.getSubject(), emailTemplate.getMessage());

    try (OutputStream os = con.getOutputStream()) {
      byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
      os.write(input, 0, input.length);
    }
   
   /* ResponseEntity<String> responseMy = null;
    try {
    	responseMy = restTemplate.getForEntity(emailTemplate.getMessage(), String.class);
    	 System.out.println("resp" + responseMy.getBody());
    } catch (Exception e) {
        System.out.println("Could not contact controller");
    }*/
    
    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
      StringBuilder response = new StringBuilder();
      String responseLine = null;

      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }
      System.out.println(response.toString());
      System.out.println(
          "Sent " + emailTemplate.getSubject() + " email to: " + emailTemplate.getAddress());
    }
  }
}
