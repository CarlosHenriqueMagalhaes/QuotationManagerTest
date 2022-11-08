package br.inatel.projects.quotation.management.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ActionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String stockId;

	@JsonManagedReference
	@OneToMany(mappedBy = "stock")
	private List<QuoteModel> quotes;

	public List<QuoteModel> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<QuoteModel> quotes) {
		this.quotes = quotes;
	}

	@PrePersist // método para salvar o ID no banco de dados
	private void onPersist() {
		this.id = UUID.randomUUID().toString();
	}

	public ActionModel() {
	}

	public ActionModel(String stockId, List<QuoteModel> quoteList) {
		this.stockId = stockId;
	}

	//adiciona uma cota a lista de cotas
	public void addQuote(QuoteModel q) {
		if (this.quotes==null) {
			 this.quotes = new ArrayList<>();
		}
		this.quotes.add( q );
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

	public void setQuoteList(List<QuoteModel> quoteList) {
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionModel other = (ActionModel) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "StockModel [id=" + id + ", stockName=" + stockId + "]";
	}

}
