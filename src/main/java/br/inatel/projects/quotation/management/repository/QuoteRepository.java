package br.inatel.projects.quotation.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.projects.quotation.management.model.QuoteModel;

public interface QuoteRepository extends JpaRepository<QuoteModel, String> {
	
	List<QuoteModel> findByStockId(String idStock);

}
