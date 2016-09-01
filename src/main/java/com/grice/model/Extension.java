package com.grice.model;

public class Extension {

	private boolean enableSearch;
	private boolean enableDuoShuo;
	private String duoShuoShortName;

	public Extension() {
	}

	public boolean isEnableSearch() {
		return enableSearch;
	}

	public void setEnableSearch(boolean enableSearch) {
		this.enableSearch = enableSearch;
	}

	public boolean isEnableDuoShuo() {
		return enableDuoShuo;
	}
	
	public void setEnableDuoShuo(boolean enableDuoShuo) {
		this.enableDuoShuo = enableDuoShuo;
	}

	public String getDuoShuoShortName() {
		return duoShuoShortName;
	}

	public void setDuoShuoShortName(String duoShuoShortName) {
		this.duoShuoShortName = duoShuoShortName;
	}

}
