package br.inatel.projects.quotation.management.form;

import java.math.BigDecimal;

/**
 * Data loaded from a stock post
 * @author francisco.carvalho
 * @since 1.0
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.inatel.projects.quotation.management.model.Quote;
import br.inatel.projects.quotation.management.model.Stock;

//para usar meu edings points no serv


public class StockForm {
	
	private String stockId;
	private Map<LocalDate, BigDecimal> quotesMap = new HashMap<>();
	
	public StockForm() {}
	
	public StockForm(String stockId, Map<LocalDate, BigDecimal> quotesMap) {
		this.stockId = stockId;
		this.quotesMap = quotesMap;
	}

	public Map<LocalDate, BigDecimal> getQuotesMap() {
		return quotesMap;
	}

	public void setQuotesMap(Map<LocalDate, BigDecimal> quotesMap) {
		this.quotesMap = quotesMap;
	}
	
	public String getStockId() {
		return stockId;
	}
	
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	public Stock convertTo() {
		return new Stock(stockId, null);
	}
	
	/**
	 * 
	 * @param stock
	 * @return List of required stock quotes
	 */
	
	public List<Quote> addQuoteList(Stock stock){
		List<Quote> quotes = new ArrayList<>();
		quotesMap.forEach((quoteDate, quotePrice) -> {
			Quote quote = new Quote(quoteDate,quotePrice ,stock);
			quotes.add(quote);
			stock.setQuoteList(quotes);
		});
		
		return stock.getQuotes();
	}

}
