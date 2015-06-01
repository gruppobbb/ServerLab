package server;

/**
 * Entry singola della lista dei punteggi: comprende di nome giocatore e rispettivo punteggio.
 * @author Giulia
 *
 */
public class ScoreEntry implements Comparable<ScoreEntry>{
	
	private String playerName;
	private long score;
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public long getScore() {
		return score;
	}
	
	public void setScore(long score) {
		this.score = score;
	}

	@Override
	public int compareTo(ScoreEntry entry) {
		if(score < entry.getScore())
			return -1;
		else if(score > entry.getScore())
			return 1;
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		ScoreEntry scoreEntry = (ScoreEntry) o;
		if((scoreEntry.getPlayerName().equals(this.getPlayerName())) &&
				(scoreEntry.getScore() == this.getScore())){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
	    return (int)score;
	  }

}
