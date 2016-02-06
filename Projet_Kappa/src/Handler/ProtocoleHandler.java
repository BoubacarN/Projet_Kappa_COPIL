package Handler;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import reponse.ResponseDeleteAndInsert;
import reponse.ResponseUpdateAndResearch;
import vues.Accounts;
import vues.Customer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProtocoleHandler {

	Socket S = getS();

	private Socket getS() {
		try {
			return new Socket("localhost", 8153);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String insert(String nom, String prenom, int age, String sexe,
			String adresse, String activity) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Customer cusJson = new Customer(nom, prenom, age, sexe, adresse,
				activity);

		String req = gson.toJson(cusJson);

		req = "NEWCUSTOMER " + req;
		PrintWriter out = new PrintWriter(S.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				S.getInputStream()));

		out.println(req);

		// to read the response
		String response = "";
		do {
			response += in.readLine() + '\n';// TODO param�tr� le temps
												// d'attente max
		} while (in.ready());
		
		
		// switch the response of the serveur
		
		try {
			switch (getStatus(response)) {
			case "KO":
				JFrame frame=new JFrame("JOptionPane showMessageDialog");
				frame.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame, "Demande non aboutie, Veuillez r�essayer");
				
				break;

			default:
				JFrame frame1=new JFrame("JOptionPane showMessageDialog");
				frame1.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame1, "Client ajout�");
				break;
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bye to the serveur
		out.print("BYE");

		in.close();
		out.close();
		S.close();

		return response;
	}
	

public String Research(int numCount) throws IOException{
	
	
		
		PrintWriter out = new PrintWriter(S.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(S.getInputStream()));
		
		
		Accounts ac1 = new Accounts(numCount);
		
		Gson gson =  new GsonBuilder().setPrettyPrinting().create() ;
		
		gson = new Gson();
		
		
		String req =gson.toJson(ac1);
		
		req = "CONSULT "+req;
		
		out.println(req);
		
		
		// to read the response
		String response = "";
		do {
			response += in.readLine() + '\n';//TODO param�tr� le temps d'attente max
		} while (in.ready());
		
		System.out.println(response);

		try {
			System.out.println(getResponse(response));
			System.out.println(response);
			JFrame frame=new JFrame("JOptionPane showMessageDialog");
			frame.setSize(new Dimension(800,800));
			JOptionPane.showMessageDialog(frame, "Votre solde est de "+getResponse(response));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// say bye to the serveur
		
		out.print("BYE");


		
		in.close();
		out.close();
		S.close();
		
		return response;
	}


	public String delete(int account_num) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Accounts cusJson = new Accounts(account_num);

		String req = gson.toJson(cusJson);

		req = "DELETE " + req;

		PrintWriter out = new PrintWriter(S.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				S.getInputStream()));

		out.println(req);

		// to read the response
		String response = "";
		do {
			response += in.readLine() + '\n';// TODO param�tr� le temps
												// d'attente max
		} while (in.ready());
		
		System.out.println(response);
		try {
			System.out.println(getStatus(response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			switch (getStatus(response)) {
			case "KO":
				JFrame frame=new JFrame("JOptionPane showMessageDialog");
				frame.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame, "Compte n'a pas �t� supprim�");
				
				break;

			default:
				JFrame frame1=new JFrame("JOptionPane showMessageDialog");
				frame1.setSize(new Dimension(800,800));
				JOptionPane.showMessageDialog(frame1, "Compte Supprim�");
				break;
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bye to the serveur
		out.print("BYE");

		in.close();
		out.close();
		S.close();

		return response;
	}

	public String Update(int numCount, int amount) throws IOException {

		PrintWriter out = new PrintWriter(S.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				S.getInputStream()));

		String response;
		Accounts ac1 = new Accounts(numCount, amount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		gson = new Gson();

		String req = gson.toJson(ac1);

		req = "WITHDRAWAL " + req;
		System.out.println(req);

		out.println(req);

		// to read the response
		response = "";
		do {
			response += in.readLine() + '\n';// TODO param�tr� le temps
												// d'attente max
		} while (in.ready());

		try {
			System.out.println(getResponse(response));
			System.out.println(response);
			JFrame frame=new JFrame("JOptionPane showMessageDialog");
			frame.setSize(new Dimension(800,800));
			JOptionPane.showMessageDialog(frame, "Votre Op�ration � bien �t� effectu�\n"
					+ "votre solde est de "+getResponse(response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bye to the serveur
		out.print("BYE");

		in.close();
		out.close();
		S.close();

		return response;
	}


	public int getResponse(String response) throws Exception {
		int balance =0 ;

		int prefixEnd = response.indexOf(' ');

		String prefix = response.substring(0, response.indexOf(' '));
		String content = response.substring(prefixEnd + 1);

		switch (prefix) {
		case "OK":
			ResponseUpdateAndResearch consultQuery;

			Gson gson = new Gson();
			
			consultQuery= gson.fromJson(content, ResponseUpdateAndResearch.class);
		
			balance = consultQuery.getBalance();
			System.out.println(consultQuery.toString());
			
			break;
		
		case "ERR":
			System.out.println("Veuillez v�rifier le num�ro de compte");
			
		break;
		default: System.out.println("Demande non aboutie");
		}

		return balance;
	}
	
	
	public String getStatus(String response) throws Exception {
	 String status = null ;

		int prefixEnd = response.indexOf(' ');

		String prefix = response.substring(0, response.indexOf(' '));
		String content = response.substring(prefixEnd + 1);

		switch (prefix) {
		case "OK":
			ResponseDeleteAndInsert consultQuery;

			Gson gson = new Gson();
			
			consultQuery= gson.fromJson(content, ResponseDeleteAndInsert.class);
		
			status = consultQuery.getStatus();
			System.out.println(consultQuery.toString());
			
			break;
		
		case "KO":
			System.out.println("Num�ro de compte invalide");
			
		break;
		default: System.out.println("Demande non aboutie");
		}

		return status;
	}

}
