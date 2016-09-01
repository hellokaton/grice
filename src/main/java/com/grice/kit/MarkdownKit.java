package com.grice.kit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.blade.kit.IOKit;

public final class MarkdownKit {
	
	public static String getContent(String path){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			boolean isContent = false;
			int count = 0;
			StringBuffer content = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if(line.indexOf("---") != -1){
					count++;
					continue;
				}
				if(count > 1){
					isContent = true;
				}
				if(isContent){
					content.append(line);
				}
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOKit.closeQuietly(br);
		}
		return "";
	}
	
}
