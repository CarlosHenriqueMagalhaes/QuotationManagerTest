package br.inatel.projects.quotation.management.model;

/**
 * Entity class
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

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
	private LocalDate dataQuote;

	@Positive
	@NotNull
	private BigDecimal quotePrice;

	@NotNull
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "stock_id")
	private Stock stock;

	/**
	 * method to save ID in database
	 */
	@PrePersist
	private void onSave() {
		this.id = UUID.randomUUID().toString();
	}

	public Quote() {
	}

	public Quote(LocalDate quoteDate, BigDecimal quotePrice, Stock stock) {
		this.dataQuote = quoteDate;
		this.quotePrice = quotePrice;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "Quote [id=" + id + ", dataQuote=" + dataQuote + ", quotePrice=" + quotePrice + "]";
	}

}
