package org.mazb.samplescrapper.model.master;

import java.util.List;
import java.util.Map;

public class Origin {
	
	private String category;
	private String id;
	private boolean isIslandTransfer;
	private List<Item> items;
	private Map<String, Route> destinationValues;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isIslandTransfer() {
		return isIslandTransfer;
	}

	public void setIslandTransfer(boolean isIslandTransfer) {
		this.isIslandTransfer = isIslandTransfer;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public class Item{
		private String label;
		private String value;
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
}
