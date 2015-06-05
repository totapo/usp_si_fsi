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

import edu.br.usp.each.si.fsi.ultimate.model.Enemy;

public class EnemyDAO {

	public static final String STORAGE_PATH = Gdx.files
			.external("tujf/xml/enemies.xml").file().getAbsolutePath();

	public static Enemy getEnemy(int id) {
		Enemy enemy = new Enemy();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(STORAGE_PATH);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("enemy");

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				if (id == Integer.parseInt(node.getAttributeValue("id"))) {
					enemy.setId_enemy_type(Integer.parseInt(node
							.getChildText("id_enemy_type")));
					enemy.setDamage(Integer.parseInt(node
							.getChildText("damage")));
					enemy.setVelocityTemp(Float.parseFloat(node
							.getChildText("velocity")));
					enemy.setId_shot(Integer.parseInt(node
							.getChildText("id_shot")));
					enemy.setId(Integer.parseInt(node.getAttributeValue("id")));
				}

			}

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return enemy;
	}

	public static List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(STORAGE_PATH);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("enemy");

			for (int i = 0; i < list.size(); i++) {

				Enemy enemy = new Enemy();
				Element node = (Element) list.get(i);

				enemy.setId_enemy_type(Integer.parseInt(node
						.getChildText("id_enemy_type")));
				enemy.setDamage(Integer.parseInt(node.getChildText("damage")));
				enemy.setVelocityTemp(Float.parseFloat(node
						.getChildText("velocity")));
				enemy.setId_shot(Integer.parseInt(node
						.getChildText("id_enemy_type")));
				enemy.setId(Integer.parseInt(node.getAttributeValue("id")));
				enemies.add(enemy);

			}

		} catch (IOException io) {
			io.printStackTrace();

		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		return enemies;

	}

	public static void CreateEnemyFile(Enemy enemy) {

		try {

			Element enemies = new Element("enemies");
			Document doc = new Document(enemies);

			Element enemyEl = new Element("enemy");

			enemyEl.setAttribute(new Attribute("id", "1"));

			enemyEl.addContent(new Element("id_shot").setText(String
					.valueOf(enemy.getId_shot())));
			enemyEl.addContent(new Element("damage").setText(String
					.valueOf(enemy.getDamage())));
			enemyEl.addContent(new Element("velocity").setText(String
					.valueOf(enemy.getVelocityTemp())));
			enemyEl.addContent(new Element("id_enemy_temp").setText(String
					.valueOf(enemy.getId_enemy_type())));

			doc.getRootElement().addContent(enemyEl);

			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(STORAGE_PATH));

		} catch (IOException io) {
			io.printStackTrace();
		}

	}

	public static void ModifyEnemyFile(Enemy enemy) {

		try {

			// enemy.setId(1);

			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(STORAGE_PATH);

			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();

			List<Element> list = rootNode.getChildren("enemy");

			for (int i = 0; i < list.size(); i++) {
				Element enemyEl = list.get(i);

				if (Integer.parseInt(enemyEl.getAttributeValue("id")) == enemy
						.getId()) {
					enemyEl.getChild("id_shot").setText(
							String.valueOf(enemy.getId_shot()));
					enemyEl.getChild("damage").setText(
							String.valueOf(String.valueOf(String.valueOf(enemy
									.getDamage()))));
					enemyEl.getChild("velocity").setText(
							String.valueOf(String.valueOf(String.valueOf(enemy
									.getVelocityTemp()))));
					enemyEl.getChild("id_enemy_type").setText(
							String.valueOf(enemy.getId_enemy_type()));

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
