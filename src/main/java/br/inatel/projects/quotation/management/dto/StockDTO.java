package br.inatel.projects.quotation.management.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.model.Quote;

public class StockDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String stockId;

	private List<QuoteDTO> quotes;

	public StockDTO() {
	}

	public StockDTO(String id, String stockId) {
		this.id = id;
		this.stockId = stockId;
	}

	// inicializando os valores do meu DTO conforme a busca que veio no obj
	// actionModel
	public StockDTO(Stock ac) {
		this.id = ac.getId();
		this.stockId = ac.getStockId();
		this.quotes = buildListQuotes(ac.getQuotes());
	}
	
	// inicializando os valores do meu DTO conforme a busca que veio no obj
	// actionModel
	public StockDTO(Stock ac, List<Quote> quotes) {
		this.id = ac.getId();
		this.stockId = ac.getStockId();
		this.quotes = buildListQuotes(quotes);
	}

	private List<QuoteDTO> buildListQuotes(List<Quote> quotes) {
		List<QuoteDTO> quotesDTO = new ArrayList<>();
		// percorrendo a lista de cotações para transformar as quotações em cotação dto
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
