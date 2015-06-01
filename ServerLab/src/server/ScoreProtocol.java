package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Protocollo per la gestione dei file dei punteggi.
 * @author Giulia
 *
 */
public class ScoreProtocol implements IProtocol{
	
	private ArrayList<ScoreEntry> scoreList;
	public static final String TEMP_FILENAME = "res/tempscores.xml";
	public static final String FILENAME = "res/globalscores.xml";
	public static final int SCORES_NUMBER = 10;
	private File tempFile;
	private File scoresFile;
	private IScoreFileManager manager;
	private int copiedLineNumber;
	private int sentLineNumber;
	
	public ScoreProtocol(IScoreFileManager manager) {
		this.manager = manager;
		copiedLineNumber = 1;
		sentLineNumber = 1;
		scoreList = new ArrayList<ScoreEntry>();
		scoresFile = new File(FILENAME);
		tempFile = new File(TEMP_FILENAME);
		tempFile.delete();
	}

	@Override
    public void copyFileString(String input) {
        try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
			writer.write(input);
			writer.newLine();
			writer.close();
			copiedLineNumber++;
		} catch (IOException e) {
			System.out.println("Exception caught when trying to copy line "
	                + copiedLineNumber);
			e.printStackTrace();
		}
    }
    
    @Override
    public void updateServerScores(){
    	scoreList.clear();
    	scoreList.addAll(manager.loadScoreFile(scoresFile));
    	for (ScoreEntry scoreEntry : manager.loadScoreFile(tempFile)) {
			if(!scoreList.contains(scoreEntry)){
				scoreList.add(scoreEntry);
			}
		}
    	Collections.sort(scoreList);
    	Collections.reverse(scoreList);
    	if(scoreList.size() >= SCORES_NUMBER){
        	scoreList.subList(SCORES_NUMBER, scoreList.size()).clear();
    	}
    	manager.saveScoreListToFile(scoresFile, scoreList);
    }
    
	@Override
	public void sendFileString(PrintWriter output) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(scoresFile));
			String fromServer;
			while ((fromServer = fileReader.readLine()) != null) {
				output.println(fromServer);
			}
			fileReader.close();
			sentLineNumber++;
		} catch (IOException e) {
			System.out.println("Exception caught when trying to copy line "
	                + sentLineNumber);
			e.printStackTrace();
		}
	}
    
}
