package br.inatel.projects.quotation.management.model;

/**
 * Entity class
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	private String description;

	@JsonManagedReference
	@OneToMany(mappedBy = "stock")
	private List<Quote> quotes;

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public Stock() {
	}

	public Stock(String description, List<Quote> quoteList) {
		this.description = description;
	}

	public void addQuote(Quote q) {
		if (this.quotes == null) {
			this.quotes = new ArrayList<>();
		}
		this.quotes.add(q);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "Stock [id=" + id + ", description=" + description + "]";
	}

}
