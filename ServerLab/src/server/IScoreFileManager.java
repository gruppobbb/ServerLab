package server;

import java.io.File;
import java.util.ArrayList;

public interface IScoreFileManager {
	
	public ArrayList<ScoreEntry> loadScoreFile(File file);
	public void saveScoreListToFile(File file, ArrayList<ScoreEntry> scoreList);

}
