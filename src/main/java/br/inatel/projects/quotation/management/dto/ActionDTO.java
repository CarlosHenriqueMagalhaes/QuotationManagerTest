package br.inatel.projects.quotation.management.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.inatel.projects.quotation.management.model.ActionModel;
import br.inatel.projects.quotation.management.model.QuoteModel;

public class ActionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String stockId;

	private List<QuoteDTO> quotes;

	public ActionDTO() {
	}

	public ActionDTO(String id, String stockId) {
		super();
		this.id = id;
		this.stockId = stockId;
	}

	// inicializando os valores do meu DTO conforme a busca que veio no obj
	// actionModel
	public ActionDTO(ActionModel ac, List<QuoteModel> quotes) {
		this.id = ac.getId();
		this.stockId = ac.getStockId();
		this.quotes = montaListaQuotes(quotes);
	}

	private List<QuoteDTO> montaListaQuotes(List<QuoteModel> quotes) {
		List<QuoteDTO> quotesDTO = new ArrayList<>();
		// percorrendo a lista de cotações para transformar as quotações em cotação dto
		for (QuoteModel quote : quotes) {
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
