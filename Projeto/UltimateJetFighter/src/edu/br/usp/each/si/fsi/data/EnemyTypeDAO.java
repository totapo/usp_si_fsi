package edu.br.usp.each.si.fsi.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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

import edu.br.usp.each.si.fsi.ultimate.model.EnemyType;

public class EnemyTypeDAO {

	public static InputStream STORAGE_FILE = Gdx.files.internal(
			"xml/enemy_type.xml").read();
	

	public static EnemyType getType(int id) {
		EnemyType type = new EnemyType();
		SAXBuilder builder = new SAXBuilder();
		

		try {

			Document document = (Document) builder.build(STORAGE_FILE);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("type");

			for (int i = 0; i < list.size(); i++) {

				Element node = (Element) list.get(i);
				if (Integer.parseInt(node.getAttributeValue("id")) == id) {
					type.setId(Integer.parseInt(node.getAttributeValue("id")));
					type.setHp(Integer.parseInt(node.getChildText("hp")));
					type.setHeight(Float.parseFloat(node.getChildText("height")));
					type.setWidth(Float.parseFloat(node.getChildText("width")));
					type.setSkin_path(node.getChildText("skin_path"));
					break;
				}

			}

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		
		
		

		return type;

	}

	public static List<EnemyType> getTypes() {
		List<EnemyType> types = new ArrayList<EnemyType>();
		SAXBuilder builder = new SAXBuilder();

		try {

			Document document = (Document) builder.build(STORAGE_FILE);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("type");

			for (int i = 0; i < list.size(); i++) {

				EnemyType type = new EnemyType();
				Element node = (Element) list.get(i);

				type.setId(Integer.parseInt(node.getChildText("id")));
				type.setHp(Integer.parseInt(node.getChildText("hp")));
				type.setHeight(Float.parseFloat(node.getChildText("height")));
				type.setWidth(Float.parseFloat(node.getChildText("width")));
				type.setSkin_path(node.getChildText("skin_path"));

				types.add(type);

			}

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		return types;

	}

	/*
	 * public static void CreateTypeFile(EnemyType type) {
	 * 
	 * try {
	 * 
	 * Element enemies = new Element("enemies"); Document doc = new
	 * Document(enemies);
	 * 
	 * Element typeEl = new Element("type");
	 * 
	 * typeEl.setAttribute(new Attribute("id", "1")); typeEl.addContent(new
	 * Element("skin_path").setText(type .getSkin_path()));
	 * typeEl.addContent(new Element("hp").setText(String.valueOf(type
	 * .getHp()))); typeEl.addContent(new
	 * Element("height").setText(String.valueOf(type .getHeight())));
	 * typeEl.addContent(new Element("width").setText(String.valueOf(type
	 * .getWidth())));
	 * 
	 * doc.getRootElement().addContent(typeEl);
	 * 
	 * // new XMLOutputter().output(doc, System.out); XMLOutputter xmlOutput =
	 * new XMLOutputter();
	 * 
	 * // display nice nice xmlOutput.setFormat(Format.getPrettyFormat());
	 * xmlOutput.output(doc, new FileWriter(STORAGE_PATH));
	 * 
	 * } catch (IOException io) { io.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public static void ModifyTypeFile(EnemyType type) {
	 * 
	 * try {
	 * 
	 * type.setId(1);
	 * 
	 * SAXBuilder builder = new SAXBuilder(); File xmlFile = new
	 * File(STORAGE_PATH);
	 * 
	 * Document doc = (Document) builder.build(xmlFile); Element rootNode =
	 * doc.getRootElement();
	 * 
	 * List<Element> list = rootNode.getChildren("type");
	 * 
	 * for (int i = 0; i < list.size(); i++) { Element jetEl = list.get(i);
	 * 
	 * if (Integer.parseInt(jetEl.getAttributeValue("id")) == type .getId()) {
	 * jetEl.getChild("skin_path").setText(type.getSkin_path());
	 * jetEl.getChild("hp").setText(
	 * String.valueOf(String.valueOf(type.getHp())));
	 * jetEl.getChild("width").setText(
	 * String.valueOf(String.valueOf(type.getWidth())));
	 * jetEl.getChild("height").setText( String.valueOf(type.getHeight()));
	 * 
	 * } }
	 * 
	 * // new XMLOutputter().output(doc, System.out); XMLOutputter xmlOutput =
	 * new XMLOutputter();
	 * 
	 * // display nice nice xmlOutput.setFormat(Format.getPrettyFormat());
	 * xmlOutput.output(doc, new FileWriter(STORAGE_PATH));
	 * 
	 * } catch (IOException io) { io.printStackTrace(); } catch (JDOMException
	 * e) {
	 * 
	 * e.printStackTrace(); }
	 */

}
