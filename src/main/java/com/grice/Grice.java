package com.grice;

import static com.blade.Blade.$;

import com.grice.kit.NodeComparator;

public class Grice {
	
	public static final NodeComparator comparator = new NodeComparator();
	
	public static void main(String[] args) throws Exception {
		$().start(Grice.class);
	}
	
}
