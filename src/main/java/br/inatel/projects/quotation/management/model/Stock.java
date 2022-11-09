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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Stock implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	@NotBlank
	private String stockId;

	@JsonManagedReference
	@OneToMany(mappedBy = "stock")
	private List<Quote> quotes;

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	@PrePersist // método para salvar o ID no banco de dados
	private void onPersist() {
		this.id = UUID.randomUUID().toString();
	}

	public Stock() {
	}

	public Stock(String stockId, List<Quote> quoteList) {
		this.stockId = stockId;
	}

	//adiciona uma cota a lista de cotas
	public void addQuote(Quote q) {
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

	public void setQuoteList(List<Quote> quoteList) {
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
		Stock other = (Stock) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "StockModel [id=" + id + ", stockName=" + stockId + "]";
	}

}
