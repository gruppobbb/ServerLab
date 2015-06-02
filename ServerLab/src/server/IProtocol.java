package server;

import java.io.PrintWriter;

/**
 * Interfaccia dei protocolli per la gestione dei file su server.
 * @author Giulia
 *
 */
public interface IProtocol {
	
	/**
     * Aggiorna il file dei punteggi globali sulla base dell'array di elementi.
     * mandati dal Client.
     */
	public void updateServerScores();
	
	/**
	 * Estrapola una entry dell'arraylist di highscores dalla stringa in entrata.
	 * @param input Stringa mandata in input dal Client 
	 */
	public void saveEntry(String input);
	
    /**
     * Manda una entry dell'arraylist di highscores sottoforma di stringa in output.
     */
	public void sendEntry(PrintWriter output);

}
