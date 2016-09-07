package com.grice.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.grice.Grice;
import com.grice.kit.MarkdownKit;

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
		Doc doc = MarkdownKit.getDoc(readme);
		this.title = doc.getTitle();
		this.isRoot = doc.isRoot();
		this.sort = doc.getSort();
		this.plain = doc.isPlain();

		File dir = new File(path);
		File[] files = dir.listFiles();
		this.docs = new ArrayList<Node>(files.length);
		for (File file : files) {
			if (!"README.md".equals(file.getName())) {
				Node node = new Node();
				Doc nodeDoc = MarkdownKit.getDoc(file.getPath());
				node.setPath(file.getPath());
				node.setTitle(nodeDoc.getTitle());
				node.setName(file.getName().replaceFirst(".md", ""));
				node.setSort(nodeDoc.getSort());
				node.setRoot(nodeDoc.isRoot());
				node.setPlain(nodeDoc.isPlain());
				docs.add(node);
			}
		}
		Collections.sort(docs, Grice.comparator);
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
		if (o.sort < this.sort) {
			return 1;
		}
		if (o.sort > this.sort) {
			return -1;
		}
		return 0;
	}

}
