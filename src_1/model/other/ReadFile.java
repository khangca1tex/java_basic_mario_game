package model.other;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class ReadFile {

	public static void luuFile(String path, PlayerSave player) {
		try {
			ArrayList<PlayerSave> p = docFile(path);
			File file = new File(path);

			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			
			p.add(player);
			for (PlayerSave player2 : p) {
				String line = player2.infoofData();
				bw.write(line);
				bw.newLine();
			}
			bw.close();
			osw.close();
			fos.close();
		} catch (Exception e) {
		}
	}

	public static ArrayList<PlayerSave> docFile(String path) {
		ArrayList<PlayerSave> player = new ArrayList<>();
		try {
			File file = new File(path);

			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();

			while (line != null) {

				String[] arr = line.split(";");
				PlayerSave p = new PlayerSave(arr[0], Integer.parseInt(arr[1]));
				player.add(p);
				line = br.readLine();
			}
			br.close();
			isr.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(player);
		return player;
	}

}
