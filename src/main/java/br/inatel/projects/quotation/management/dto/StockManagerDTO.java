package br.inatel.projects.quotation.management.dto;

/**
 * DTO (Data transfer object) for Stock Manager
 * 
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

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
