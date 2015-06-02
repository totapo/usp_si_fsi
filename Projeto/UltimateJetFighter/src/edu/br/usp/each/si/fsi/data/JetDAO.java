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
import com.badlogic.gdx.files.FileHandle;

import edu.br.usp.each.si.fsi.ultimate.model.Jet;

public class JetDAO {

	public static final String STORAGE_PATH = Gdx.files.external("jets.xml")
			.file().getAbsolutePath();

	public static List<Jet> getJets() throws IOException, JDOMException {
		List<Jet> jets = new ArrayList<Jet>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("xml/jet.xml");

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("item");

			for (int i = 0; i < list.size(); i++) {

				Jet jet = new Jet();
				Element node = (Element) list.get(i);

				jet.setSize(Integer.parseInt(node.getChildText("size")));
				jet.setSkin_path(node.getChildText("skin_path"));
				jet.setHp(Integer.parseInt(node.getChildText("hp")));
				jet.setDefense(Integer.parseInt(node.getChildText("defense")));
				jet.setDamage(Integer.parseInt(node.getChildText("damage")));
				jet.setLvl(Integer.parseInt(node.getChildText("lvl")));
				jet.setXp(Integer.parseInt(node.getChildText("xp")));
				jets.add(jet);

			}

			return jets;

		} catch (IOException io) {
			throw io;
		} catch (JDOMException jdomex) {
			throw jdomex;
		} catch (NumberFormatException ex) {
			throw ex;
		}

	}

	public static void CreateJetFile(Jet jet) {

		try {

			Element jets = new Element("user-jets");
			Document doc = new Document(jets);

			Element jetEl = new Element("jet");

			jetEl.setAttribute(new Attribute("id", "1"));
			jetEl.addContent(new Element("size").setText(String.valueOf(jet
					.getSize())));
			jetEl.addContent(new Element("skin_path").setText(jet
					.getSkin_path()));
			jetEl.addContent(new Element("hp").setText(String.valueOf(jet
					.getHp())));
			jetEl.addContent(new Element("defense").setText(String.valueOf(jet
					.getDefense())));
			jetEl.addContent(new Element("damage").setText(String.valueOf(jet
					.getDamage())));
			jetEl.addContent(new Element("lvl").setText(String.valueOf(jet
					.getLvl())));
			jetEl.addContent(new Element("xp").setText(String.valueOf(jet
					.getXp())));

			doc.getRootElement().addContent(jetEl);

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(STORAGE_PATH));

		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	public static void ModifyJetFile(Jet jet) {

		try {

			jet.setId(1);

			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(STORAGE_PATH);

			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();

			List<Element> list = rootNode.getChildren("jet");

			for (int i = 0; i < list.size(); i++) {
				Element jetEl = list.get(i);

				if (Integer.parseInt(jetEl.getAttributeValue("id")) == jet
						.getId()) {
					jetEl.getChild("skin_path").setText(jet.getSkin_path());
					jetEl.getChild("hp").setText(
							String.valueOf(String.valueOf(jet.getHp())));
					jetEl.getChild("defense").setText(
							String.valueOf(String.valueOf(jet.getDefense())));
					jetEl.getChild("damage").setText(
							String.valueOf(jet.getDamage()));
					jetEl.getChild("lvl").setText(String.valueOf(jet.getLvl()));
					jetEl.getChild("xp").setText(String.valueOf(jet.getXp()));


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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
