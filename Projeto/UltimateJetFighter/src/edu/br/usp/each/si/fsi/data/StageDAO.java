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

import edu.br.usp.each.si.fsi.ultimate.model.Stage;

public class StageDAO {

	public static final String STORAGE_PATH = Gdx.files.external("stages.xml")
			.file().getAbsolutePath();

	public static Stage getStage(int id) {
		Stage stage = new Stage();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(STORAGE_PATH);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("stage");

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				if (Integer.parseInt(node.getAttributeValue("id")) == id) {

					stage.setId(Integer.parseInt(node.getAttributeValue("id")));
					stage.setTime(Float.parseFloat(node.getChildText("time")));
					stage.setLvl(Integer.parseInt(node.getChildText("lvl")));
					stage.setId_boss(Integer.parseInt(node
							.getChildText("id_boss")));
					stage.setSource(node.getChildText("source"));
				}

			}

		} catch (IOException io) {
			io.printStackTrace();
			;
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
			;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return stage;

	}

	public static List<Stage> getStages() {
		List<Stage> stages = new ArrayList<Stage>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(STORAGE_PATH);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("stage");

			for (int i = 0; i < list.size(); i++) {

				Stage stage = new Stage();
				Element node = (Element) list.get(i);

				stage.setId(Integer.parseInt(node.getAttributeValue("id")));
				stage.setTime(Float.parseFloat(node.getChildText("time")));
				stage.setLvl(Integer.parseInt(node.getChildText("lvl")));
				stage.setId_boss(Integer.parseInt(node.getChildText("id_boss")));
				stage.setSource(node.getChildText("source"));
				stages.add(stage);

			}

		} catch (IOException io) {
			io.printStackTrace();
			;
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
			;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return stages;

	}

	public static void CreateStageFile(Stage stage) {

		try {

			Element stages = new Element("stages");
			Document doc = new Document(stages);

			Element stageEl = new Element("stage");

			stageEl.setAttribute(new Attribute("id", "1"));
			stageEl.addContent(new Element("source").setText(stage.getSource()));
			stageEl.addContent(new Element("lvl").setText(String.valueOf(stage
					.getLvl())));
			stageEl.addContent(new Element("id_boss").setText(String
					.valueOf(stage.getId_boss())));
			stageEl.addContent(new Element("time").setText(String.valueOf(stage
					.getTime())));

			doc.getRootElement().addContent(stageEl);

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(STORAGE_PATH));

		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	public static void ModifyJetFile(Stage stage) {

		try {

			stage.setId(1);

			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(STORAGE_PATH);

			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();

			List<Element> list = rootNode.getChildren("stage");

			for (int i = 0; i < list.size(); i++) {
				Element stageEl = list.get(i);

				if (Integer.parseInt(stageEl.getAttributeValue("id")) == stage
						.getId()) {
					stageEl.getChild("source").setText(stage.getSource());
					stageEl.getChild("lvl").setText(
							String.valueOf(String.valueOf(stage.getLvl())));
					stageEl.getChild("time").setText(
							String.valueOf(String.valueOf(stage.getTime())));
					stageEl.getChild("id_boss").setText(
							String.valueOf(stage.getId_boss()));

				}
			}

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(STORAGE_PATH));

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}

	}

}
