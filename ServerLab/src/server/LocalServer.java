package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server che gira in locale. Accetta solo una connessione per volta e poi si spegne.
 * WIP? WIP.
 * @author Giulia
 *
 */

public class LocalServer {
	
	private static final int PORT = 8080;
	
	/**
	 * Avvia il server.
	 */
	public void start(){
			
        try ( 
                ServerSocket serverSocket = new ServerSocket(PORT);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            ) {
				IProtocol protocol = new ScoreProtocol(new ScoreXMLFileManager());
				
				String fromUser;
				while ((fromUser = in.readLine()) != null) {
					if(fromUser.equals("SENDING SCORES ONLINE")){
						break;
					}else{
						protocol.copyFileString(fromUser);
					}
				}
				protocol.updateServerScores();
				protocol.sendFileString(out);

        } catch (IOException e) {
			System.out
			.println("Exception caught when trying to listen on port "
					+ PORT + " or listening for a connection");
			e.printStackTrace();
		}
	}
	

}
