package edu.br.usp.each.si.fsi.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.badlogic.gdx.Gdx;


public class ScoreDAO {
	public static final String STORAGE_PATH = Gdx.files.external("score.xml")
			.file().getAbsolutePath();

	@SuppressWarnings("finally")
	public static int[] getScore() {
		if (!Gdx.files.external("score.xml").file().exists()) {
			CreateScoreFile(new int[5]);
		}
		int[] scores = new int[5];
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(STORAGE_PATH);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("score");
			Element node = (Element) list.get(0);

			scores[0] = (Integer.parseInt(node.getChildText("first")));
			scores[1] = (Integer.parseInt(node.getChildText("second")));
			scores[2] = (Integer.parseInt(node.getChildText("third")));
			scores[3] = (Integer.parseInt(node.getChildText("fourth")));
			scores[4] = (Integer.parseInt(node.getChildText("fifth")));
			return scores;

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		finally{
			return scores;
		}

	}

	public static void CreateScoreFile(int[] scores) {

		try {

			Element scoresEl = new Element("user-scores");
			Document doc = new Document(scoresEl);

			Element scoreEl = new Element("score");

			scoreEl.addContent(new Element("first").setText(String
					.valueOf(scores[0])));
			scoreEl.addContent(new Element("second").setText(String
					.valueOf(scores[1])));
			scoreEl.addContent(new Element("third").setText(String
					.valueOf(scores[2])));
			scoreEl.addContent(new Element("fourth").setText(String
					.valueOf(scores[3])));
			scoreEl.addContent(new Element("fifth").setText(String
					.valueOf(scores[4])));

			doc.getRootElement().addContent(scoreEl);

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(STORAGE_PATH));

		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	public static void ModifyScoreFile(int n) {

		int[] scores = getScore();
		int[] scoresTemp = new int[5];
		for (int i = 0; i < scores.length; i++) {
			if (n > scores[i]) {
				scoresTemp[i] = n;
				for (int j = i + 1; j < 5; j++) {
					scoresTemp[j] = scores[j - 1];
				}
				break;
			} else {
				scoresTemp[i] = scores[i];
			}
		}
		scores = scoresTemp;
		CreateScoreFile(scores);

	}
}
