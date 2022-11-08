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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class QuoteModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	private LocalDate date;

	@NotNull
	private BigDecimal valor;

	@NotNull
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "stock_id")
	private ActionModel stock;

	@PrePersist // método para salvar o ID no banco de dados
	private void onSave() {
		this.id = UUID.randomUUID().toString();
	}

	public QuoteModel() {
	}

	public QuoteModel(LocalDate date, BigDecimal valor, ActionModel stock) {
		this.date = date;
		this.valor = valor;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ActionModel getStock() {
		return stock;
	}

	public void setStock(ActionModel stock) {
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
		QuoteModel other = (QuoteModel) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "QuoteModel [id=" + id + ", date=" + date + ", valor=" + valor + "]";
	}

}
