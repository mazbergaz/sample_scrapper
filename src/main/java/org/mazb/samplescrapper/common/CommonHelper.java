package org.mazb.samplescrapper.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.NumberFormat;

public class CommonHelper {

	public static void writeToFile(String filename, String content) {
		try {

			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getFormattedCurrency(String currency, long amt){
		String result = null;
		if(amt!=0){
			result = currency;
			NumberFormat nf = NumberFormat.getInstance();
			result = result+" "+nf.format(amt);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getFormattedCurrency("IDR", 0));
	}

}
