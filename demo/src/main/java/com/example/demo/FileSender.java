package com.example.demo;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

class FileSender extends Thread {
	private Socket socket;
	private FileInputStream fis;
	private BufferedOutputStream bos;

	private String filename;

	private OutputStream os;

	private InputStream is;
	private BufferedInputStream bis;

	private int fileSize;
	
	private JsonObject result;

	public FileSender(Socket socket, String filestr) {
		this.socket = socket;
		this.filename = filestr;
		try {
			// �뜲�씠�꽣 �뒪�듃?���? �깮�꽦
			this.os = socket.getOutputStream();
			bos = new BufferedOutputStream(os);
			this.is = socket.getInputStream();
			bis = new BufferedInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean sendFileSize(String fileName) throws IOException {
		File imageFile = new File(fileName);
		fileSize = (int) imageFile.length() * 100;
		fis = new FileInputStream(imageFile);
		bos.write(Integer.toString(fileSize).getBytes());
		bos.flush();
		System.out.println("send file size : " + fileSize);
		return true;
	}

	public boolean sendFileName(JsonObject fileName) throws IOException {
		bos.write(fileName.toString().getBytes());
		bos.flush();
		//System.out.println("send file size : " + fileSize);
		return true;
	}
	

	public void sendImage(int fileSize) throws IOException {
		byte[] data = new byte[(int) (fileSize)];
		bos.write(data, 0, fis.read(data));
		System.out.println("send image ... ");
		bos.flush();
		fis.close();
	}

	public String receiveData(int buffer_size) throws IOException {
		byte[] tmp = new byte[buffer_size];
		int zz = bis.read(tmp);
		//System.out.println("server : "+new String(tmp, 0, zz));
		return new String(tmp, 0, zz);
	}
	
	public JsonObject getResult() {
		return result;
	}
	
	public void close() {
		try {
		bos.close();
		socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		JsonObject jo = new JsonObject();
		try {
	        jo.addProperty("filename",  filename );
			sendFileName(jo);
			String res = receiveData(1024);
			JsonObject outJo = (JsonObject) new Gson().fromJson(res,JsonObject.class);
			result = outJo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}// finally
	}// run

}

