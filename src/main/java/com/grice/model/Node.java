package com.grice.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.blade.kit.IOKit;

public class Node implements Comparable<Node> {
	
	private int sort;
	private String title;
	private String name;
	private String path;
	private boolean plain;
	private boolean isRoot;
	private List<Node> docs;
	
	public Node() {
	}

	public Node(String path) {
		this.path = path;
		String readme = path + File.separatorChar + "README.md";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(readme));
			String line;
			boolean isContent = false;
			int count = 0;
			while ((line = br.readLine()) != null) {
				if(line.indexOf("---") != -1){
					count++;
					continue;
				}
				if(count > 1){
					isContent = true;
				}
				if(!isContent && line.indexOf("title") != -1){
					int pos = line.indexOf(":");
					this.title = line.substring(pos + 1).trim();
				}
				if(!isContent && line.indexOf("sort") != -1){
					int pos = line.indexOf(":");
					this.sort = Integer.valueOf(line.substring(pos + 1).trim());
				}
				if(!isContent && line.indexOf("root") != -1){
					int pos = line.indexOf(":");
					this.isRoot = Boolean.valueOf(line.substring(pos + 1).trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOKit.closeQuietly(br);
		}
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getDocs() {
		return docs;
	}

	public void setDocs(List<Node> docs) {
		this.docs = docs;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isPlain() {
		return plain;
	}

	public void setPlain(boolean plain) {
		this.plain = plain;
	}

	@Override
	public int compareTo(Node o) {
		if(o.sort > this.sort){
			return 1;
		}
		if(o.sort < this.sort){
			return -1;
		}
		return 0;
	}
	
}
