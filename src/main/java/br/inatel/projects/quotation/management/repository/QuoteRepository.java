package br.inatel.projects.quotation.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.projects.quotation.management.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {
	
//	select * from quote where quote.stock_id = 'vale5'
	List<Quote> findByStockId(String idStock); //query montada pelo jpa
	
//query montada nativamente 
//	@Query(value = "select * from quote where quote.stock_id = :idStock" , nativeQuery = true)
//	List<Quote> findByStockId(@Param("idStock") String idStock);

	
}
