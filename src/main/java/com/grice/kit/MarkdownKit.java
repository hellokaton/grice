package com.grice.kit;

import com.blade.kit.FileKit;
import com.blade.kit.IOKit;
import com.grice.model.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class MarkdownKit {

	public static Node getNodeDoc(String path) {
		BufferedReader br = null;
		try {
			if (!FileKit.exist(path)) {
				return null;
			}

			File file = new File(path);

			Node doc = new Node();
			doc.setPath(file.getPath());
			doc.setName(file.getName().replace(".md", ""));
			br = new BufferedReader(new FileReader(file));
			String line;
			boolean isContent = false;
			int count = 0;
			StringBuffer content = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (line.indexOf("---") != -1) {
					count++;
					continue;
				}
				if (count > 1 && line.indexOf("{:toc}") != -1) {
					isContent = true;
					continue;
				}
				if (!isContent && line.indexOf("title") != -1) {
					int pos = line.indexOf(":");
					doc.setTitle(line.substring(pos + 1).trim());
					continue;
				}
				if (!isContent && line.indexOf("sort") != -1) {
					int pos = line.indexOf(":");
					doc.setSort(Integer.valueOf(line.substring(pos + 1).trim()));
					continue;
				}
				if (!isContent && line.indexOf("plain") != -1) {
					int pos = line.indexOf(":");
					doc.setPlain(Boolean.valueOf(line.substring(pos + 1).trim()));
					continue;
				}
				if (!isContent && line.indexOf("root") != -1) {
					int pos = line.indexOf(":");
					doc.setRoot(Boolean.valueOf(line.substring(pos + 1).trim()));
					continue;
				}
				if (isContent) {
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
