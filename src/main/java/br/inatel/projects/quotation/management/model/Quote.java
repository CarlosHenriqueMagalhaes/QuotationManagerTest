package br.inatel.projects.quotation.management.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Quote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	private LocalDate quoteDate;

	@Positive
	@NotNull
	private BigDecimal quotePrice;

	@NotNull
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "stock_id")
	private Stock stock;

	@PrePersist // método para salvar o ID no banco de dados
	private void onSave() {
		this.id = UUID.randomUUID().toString();
	}

	public Quote() {
	}

	public Quote(LocalDate quoteDate, BigDecimal quotePrice, Stock stock) {
		this.quoteDate = quoteDate;
		this.quotePrice = quotePrice;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(LocalDate quoteDate) {
		this.quoteDate = quoteDate;
	}

	public BigDecimal getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(BigDecimal quotePrice) {
		this.quotePrice = quotePrice;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
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
		Quote other = (Quote) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "QuoteModel [id=" + id + ", date=" + quoteDate + ", valor=" + quotePrice + "]";
	}

}
