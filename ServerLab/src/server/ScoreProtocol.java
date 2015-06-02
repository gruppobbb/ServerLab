package server;

import java.io.File;
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
	public static final String FILENAME = "res/globalscores.xml";
	public static final int MAX_SCORES = 10;
	private File scoresFile;
	private IScoreFileManager manager;
	
	/**
	 * @param manager {@link IScoreFileManager}
	 */
	public ScoreProtocol(IScoreFileManager manager) {
		this.manager = manager;
		scoreList = new ArrayList<ScoreEntry>();
		scoresFile = new File(FILENAME);
	}

	/**
	 * @see IProtocol
	 */
	@Override
    public void saveEntry(String input) {
        ScoreEntry entry = new ScoreEntry();
        String[] entryValues = input.split(",");
        entry.setPlayerName(entryValues[0]);
        entry.setScore(Long.parseLong(entryValues[1]));
        scoreList.add(entry);
    }
    
	/**
	 * @see IProtocol
	 */
    @Override
    public void updateServerScores(){
    	for (ScoreEntry scoreEntry : manager.loadScoreFile(scoresFile)) {
			if(!scoreList.contains(scoreEntry)){
				scoreList.add(scoreEntry);
			}
		}
    	Collections.sort(scoreList);
    	Collections.reverse(scoreList);
    	if(scoreList.size() >= MAX_SCORES){
        	scoreList.subList(MAX_SCORES, scoreList.size()).clear();
    	}
    	manager.saveScoreListToFile(scoresFile, scoreList);
    }
    
    /**
     * @see IProtocol
     */
	@Override
	public void sendEntry(PrintWriter output) {
		for (int i = 0; i < MAX_SCORES; i++) {
			ScoreEntry entry = scoreList.get(i);
			output.println(entry.getPlayerName() + "," + entry.getScore());
		}
		scoreList.clear();
	}
    
}
