package br.inatel.projects.quotation.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.repository.StockRepository;

@Transactional
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	public StockService(StockRepository actionRepository) {
		this.stockRepository = actionRepository;
	}

	public List<Stock> listAllStock() {
		List<Stock> stock = stockRepository.findAll();
		return stock;
	}

	public Optional<Stock> findById(String id) {
		return stockRepository.findById(id);
	}

	public Stock findByStockId(String idStock) {
		Stock ac = stockRepository.findByStockId(idStock);
		return ac;
	}

}