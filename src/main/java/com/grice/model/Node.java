package com.grice.model;

import com.grice.kit.MarkdownKit;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
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
		this.docs = new ArrayList<>(files.length);
		for (File file : files) {
			if (!"README.md".equals(file.getName())) {
				Node node = MarkdownKit.getNodeDoc(file.getPath());
				node.parent_name = this.name;
				docs.add(node);
			}
		}
		docs.sort(Comparator.comparingInt(Node::getSort));
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.sort, o.sort);
	}

}
