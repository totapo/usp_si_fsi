package edu.br.usp.each.si.fsi.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.badlogic.gdx.Gdx;

public class StageInfoDAO {
	public static final InputStream STORAGE_FILE = Gdx.files.internal("stages/xml/stage1.xml").read();

	public static List<StageEnemyInfo> getStageEnemiesInfo(int stageNumber) {
		List<StageEnemyInfo> enemiesInfo = new ArrayList<StageEnemyInfo>();
			
		SAXBuilder builder = new SAXBuilder();

		try {
			Document document = (Document) builder.build(STORAGE_FILE);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("enemy_info");

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				StageEnemyInfo enemyInfo = new StageEnemyInfo();
				enemyInfo.setMax_number(Integer.parseInt(node.getChildText("max_number")));
				enemyInfo.setId_shot(Integer.parseInt(node.getChildText("id_shot")));
				enemyInfo.setId_enemy(Integer.parseInt(node.getChildText("id_enemy")));
				enemiesInfo.add(enemyInfo);
			}

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			jdomex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return enemiesInfo;

	}

}
