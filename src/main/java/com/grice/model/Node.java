package com.grice.model;

import com.grice.bootstrap.Constant;
import com.grice.kit.MarkdownKit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node implements Comparable<Node> {

	private int sort;
	private String title;
	private String name;
	private String parent_name;
	private String path;
	private String content;
	private boolean plain;
	private boolean isRoot;
	private List<Node> docs;

	public Node() {
	}

	public Node(String name, String path) {
		this.name = name;
		this.path = path;
		String readme = path + File.separatorChar + "README.md";
		Node doc = MarkdownKit.getNodeDoc(readme);
		this.title = doc.getTitle();
		this.isRoot = doc.isRoot();
		this.sort = doc.getSort();
		this.plain = doc.isPlain();

		File dir = new File(path);
		File[] files = dir.listFiles();
		this.docs = new ArrayList<Node>(files.length);
		for (File file : files) {
			if (!"README.md".equals(file.getName())) {
				Node node = MarkdownKit.getNodeDoc(file.getPath());
				node.parent_name = this.name;
				docs.add(node);
			}
		}
		Collections.sort(docs, Constant.comparator);
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	@Override
	public int compareTo(Node o) {
		if (o.sort < this.sort) {
			return 1;
		}
		if (o.sort > this.sort) {
			return -1;
		}
		return 0;
	}

}
