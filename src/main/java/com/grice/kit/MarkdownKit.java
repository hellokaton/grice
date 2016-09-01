package com.grice.kit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.blade.kit.IOKit;
import com.grice.model.Doc;

public final class MarkdownKit {
	
	public static Doc getContent(String path){
		BufferedReader br = null;
		try {
			Doc doc = new Doc();
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
				if(!isContent && line.indexOf("name") != -1){
					int pos = line.indexOf(":");
					doc.setTitle(line.substring(pos + 1).trim());
				}
				if(!isContent && line.indexOf("sort") != -1){
					int pos = line.indexOf(":");
					doc.setSort(Integer.valueOf(line.substring(pos + 1).trim()));
				}
				if(!isContent && line.indexOf("root") != -1){
					int pos = line.indexOf(":");
					doc.setRoot(Boolean.valueOf(line.substring(pos + 1).trim()));
				}
				if(isContent){
					content.append(line).append("\r\n");
				}
			}
			doc.setContent(content.toString());
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOKit.closeQuietly(br);
		}
		return null;
	}
	
}
