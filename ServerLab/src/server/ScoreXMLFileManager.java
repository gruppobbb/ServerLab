package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Classe per la gestione particolare dei file dei punteggi XML.
 * @author Giulia
 */
public class ScoreXMLFileManager implements IScoreFileManager{
	
	/**
	 * @see IScoreFileManager
	 */
	@Override
	public ArrayList<ScoreEntry> loadScoreFile(File fileXML){
		ArrayList<ScoreEntry> scoreList = new ArrayList<ScoreEntry>();
    	try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(fileXML);
			
			NodeList scoreNodesList = xmlDoc.getElementsByTagName("entry");
			for (int i = 0; i < ScoreProtocol.MAX_SCORES; i++) {
				Node scoreNode = scoreNodesList.item(i);				
				NodeList scoreInfo = scoreNode.getChildNodes();
				ScoreEntry scoreEntry = new ScoreEntry();
				for (int j = 0; j < scoreInfo.getLength(); j++) {
					Node info = scoreInfo.item(j);
					if(info.getNodeName() == "player"){
						scoreEntry.setPlayerName(info.getTextContent());
					}else if(info.getNodeName() == "score"){
						scoreEntry.setScore(Long.parseLong(info.getTextContent()));
					}
				}
				scoreList.add(scoreEntry);
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scoreList;
    }
    
	/**
	 * @see IScoreFileManager
	 */
	@Override
    public void saveScoreListToFile(File file, ArrayList<ScoreEntry> scoreList){
    	try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = dbFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(file);
			
			NodeList scoreNodesList = xmlDoc.getElementsByTagName("entry");
			for (int i = 0; i < ScoreProtocol.MAX_SCORES; i++) {
				Node scoreNode = scoreNodesList.item(i);				
				NodeList scoreInfo = scoreNode.getChildNodes();
				for (int j = 0; j < scoreInfo.getLength(); j++) {
					Node info = scoreInfo.item(j);
					if (info.getNodeName() == "score") {
						info.setTextContent(Long.toString(scoreList.get(i).getScore()));
					} else if (info.getNodeName() == "player") {
						info.setTextContent(scoreList.get(i).getPlayerName());
					}
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

}
