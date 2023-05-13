package sms;
import java.net.*;
import java.io.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;


public class WhatsappIntegration {
		   
	   public static void main( String[] args )
	   {
	       try {
	           HttpRequest request = HttpRequest.newBuilder()
	               .uri(new URI("https://graph.facebook.com/v13.0/102767522824268/messages"))
	               .header("Authorization", "Bearer EAAH0SinfI8IBAM3040xziAumpCCALC6KZCHQXXMSndMmwSjhesdJmPW2ydZCOH6GCusUIfqPuBZAkpJCFbF9YCt5ieBh4RaRkCDWiKHO10lbTUZBZCZCBwLgdZAa9xPZAAnbm2l0jNA1T3YyCpFkHktaNKUhZCiMfd0BPUNUdQ6GydkW3yRmtjZABoI5eg4Ecb1wav9QByEeYWilRwofiRoZAvrZCClI1Mcu3oWo1gtv1Aa64wZDZD")
	               .header("Content-Type", "application/json")
	              .POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"<+919000590085>\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }"))
	              //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"<+919000590085>\", \"type\": \"text\", \"text\": { \"preview_url\": false, \"body\": \"This is an example of a text message\" } }"))
	               .build();
	           HttpClient http = HttpClient.newHttpClient();
	           HttpResponse<String> response = http.send(request,BodyHandlers.ofString());
	           System.out.println(response.body());
	          
	       } catch (URISyntaxException | IOException | InterruptedException e) {
	           e.printStackTrace();
	       }
	   }
	}



