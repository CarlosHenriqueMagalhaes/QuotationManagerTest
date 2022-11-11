package br.inatel.projects.quotation.management.dto;

/**
 * DTO (Data transfer object) for Stock Class
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.model.Quote;

public class StockDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String stockId;

	private List<QuoteDTO> quotes = new ArrayList<>();

	public StockDTO() {
	}

	public StockDTO(String id, String stockId) {
		this.id = id;
		this.stockId = stockId;
	}

	public StockDTO(Stock ac) {
		this.id = ac.getId();
		this.stockId = ac.getDescription();
		this.quotes = buildListQuotes(ac.getQuotes());
	}

	public StockDTO(Stock ac, List<Quote> quotes) {
		this.id = ac.getId();
		this.stockId = ac.getDescription();
		this.quotes = buildListQuotes(quotes);
	}

	public StockDTO(List<QuoteDTO> quotes2) {
		this.id = quotes2.get(0).getId();
		this.stockId = quotes2.get(0).getStockId();
		this.quotes = quotes2;
	}

	private List<QuoteDTO> buildListQuotes(List<Quote> quotes) {
		List<QuoteDTO> quotesDTO = new ArrayList<>();
		for (Quote quote : quotes) {
			QuoteDTO qt = new QuoteDTO(quote);
			quotesDTO.add(qt);
		}

		return quotesDTO;
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

	public List<QuoteDTO> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<QuoteDTO> quotes) {
		this.quotes = quotes;
	}

}
