package br.inatel.projects.quotation.management.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.inatel.projects.quotation.management.model.Quote;

public class QuoteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private LocalDate quoteDate;

	private BigDecimal quotePrice;

	private String stockId;

	public QuoteDTO() {
	}

	public QuoteDTO(String id, String stockId) {
		super();
		this.id = id;
		this.stockId = stockId;
	}

	public QuoteDTO(Quote quote) {
		this.id = quote.getId();
		this.quoteDate = quote.getDate();
		this.quotePrice = quote.getValor();
		this.stockId = quote.getStock().getStockId();
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
		return quoteDate;
	}

	public void setDate(LocalDate date) {
		this.quoteDate = date;
	}

	public BigDecimal getValor() {
		return quotePrice;
	}

	public void setValor(BigDecimal valor) {
		this.quotePrice = valor;
	}

}