package br.inatel.projects.quotation.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.inatel.projects.quotation.management.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

	@Query(value = "select * from stock left join quote on quote.stock_id = stock.id" , nativeQuery = true)
	List<Stock> findAll();
	
}
