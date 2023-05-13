package sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSpush {
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String message = "If you want to send non text message, you should look to method sendDataMessage.";      
		String phone = "9000590085";
		String username = "naresh";
		String password = "Nari@123";
		String address = "http://192.168.1.2";
		String port = "8090";

		URL url = new URL(
		        address+":"+port+"/SendSMS?username="+username+"&password="+password+
		        "&phone="+phone+"&message="+URLEncoder.encode(message,"UTF-8"));

		URLConnection connection = url.openConnection();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while((inputLine = bufferedReader.readLine()) !=null){
		    System.out.println(inputLine);
		}
		bufferedReader.close();
		}

}
