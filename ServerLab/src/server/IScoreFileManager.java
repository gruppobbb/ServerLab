package server;

import java.io.File;
import java.util.ArrayList;

public interface IScoreFileManager {
	
	/**
	 * Carica i punteggi da file.
	 * @param file File da cui caricare i punteggi
	 * @return ArrayList che comprende tutti i punteggi degli Highscores
	 */
	public ArrayList<ScoreEntry> loadScoreFile(File file);
	
	/**
	 * Salva i punteggi su file.
	 * @param file File su cui salvare i punteggi
	 * @param scoreList ArrayList da cui leggere i punteggi da salvare
	 */
	public void saveScoreListToFile(File file, ArrayList<ScoreEntry> scoreList);

}
