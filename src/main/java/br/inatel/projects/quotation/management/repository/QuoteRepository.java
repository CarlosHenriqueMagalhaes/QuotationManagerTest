package br.inatel.projects.quotation.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.projects.quotation.management.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {
	
	List<Quote> findByStockId(String idStock);

}
