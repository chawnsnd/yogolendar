package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HelpUtil {
	public static void MainHelp() {
		String resource = "src/resources/help/main-help.txt";
		File file = new File(resource);
		try(FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr)){
			while(true) {
				String line = br.readLine();
				if(line == null) break;
				System.out.println(line);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
