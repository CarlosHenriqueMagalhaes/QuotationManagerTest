package br.inatel.projects.quotation.management.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.inatel.projects.quotation.management.model.QuoteModel;

public class QuoteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private LocalDate date;

	private double valor;

	private String stockId;

	public QuoteDTO() {
	}

	public QuoteDTO(String id, String stockId) {
		super();
		this.id = id;
		this.stockId = stockId;
	}

	public QuoteDTO(QuoteModel quote) {
		this.id = quote.getId();
		this.date = quote.getDate();
		this.valor = quote.getValor();
		this.stockId = quote.getStock().getStockName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
