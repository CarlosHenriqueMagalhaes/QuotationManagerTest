package br.inatel.projects.quotation.management.dto;

public class StockManagerDTO {

	private String id;
	private String description;

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public boolean contains(String string) {
		if (this.id.equals(string)) {
			return true;
		}
		return false;
	}

}
