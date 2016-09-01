package com.grice.kit;

import java.util.Comparator;

import com.grice.model.Node;

public class NodeComparator implements Comparator<Node> {
	public int compare(Node o1, Node o2) {
		if (o1.getSort() > o2.getSort())
			return 1;
		if (o1.getSort() < o2.getSort())
			return -1;
		return 0;
	}
}