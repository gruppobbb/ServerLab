package server;

import java.io.PrintWriter;

/**
 * Interfaccia dei protocolli per la gestione dei file su server.
 * @author Giulia
 *
 */
public interface IProtocol {
	
	/**
     * Aggiorna il file dei punteggi globali sulla base del file temporaneo
     * mandato dal Client.
     */
	public void updateServerScores();
	
	/**
	 * Copia una stringa in un file temporaneo.
	 * @param input Stringa mandata in input dal Client 
	 */
	public void copyFileString(String input);
	
    /**
     * Legge dal file dei punteggi globali una stringa e la manda in output al Client.
     */
	public void sendFileString(PrintWriter output);

}
