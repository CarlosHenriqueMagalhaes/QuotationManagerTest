package br.inatel.projects.quotation.management.dto;

/**
 * DTO (Data transfer object) for Quote Class
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.inatel.projects.quotation.management.model.Quote;

public class QuoteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private LocalDate dataQuote;

	private BigDecimal quotePrice;

	private String stockId;

	public QuoteDTO() {
	}

	public QuoteDTO(String id, String stockId) {
		this.id = id;
		this.stockId = stockId;
	}

	public QuoteDTO(Quote quote) {
		this.id = quote.getId();
		this.dataQuote = quote.getDataQuote();
		this.quotePrice = quote.getQuotePrice();
		this.stockId = quote.getStock().getId();
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

	public LocalDate getDataQuote() {
		return dataQuote;
	}

	public void setDataQuote(LocalDate dataQuote) {
		this.dataQuote = dataQuote;
	}

	public BigDecimal getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(BigDecimal quotePrice) {
		this.quotePrice = quotePrice;
	}

}