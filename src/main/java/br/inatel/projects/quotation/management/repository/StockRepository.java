package br.inatel.projects.quotation.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.inatel.projects.quotation.management.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

	Stock findByStockId(String stockId);

}
