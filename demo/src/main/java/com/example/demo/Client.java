package com.example.demo;

import java.net.Socket;

import com.example.demo.FileSender;
import com.google.gson.JsonObject;

public class Client {
	Socket socket = null;
	String serverIp = "127.0.0.1";
	int serverPort = 5000;
	String fileName;
	JsonObject result;

	public Client(String fileName) {
		this.fileName = fileName;

		try {
			// �꽌踰� �뿰寃�
			socket = new Socket(serverIp, serverPort); // socket(),connect();
			System.out.println(serverIp + " : " + serverPort);

			FileSender fileSender = new FileSender(socket, fileName);
			fileSender.start();
			fileSender.join();
			
			
			result = fileSender.getResult();
			//System.out.println(result.get("imgpath"));

			//System.out.println("result from server : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}// catch
	}
	
	public JsonObject getResult() {
		return result;
	}

}
