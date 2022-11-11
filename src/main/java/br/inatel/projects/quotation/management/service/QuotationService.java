package br.inatel.projects.quotation.management.service;

import java.util.ArrayList;

/**
 * Service class, where the methods to be called in the Controller class are located
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.inatel.projects.quotation.management.dto.QuoteDTO;
import br.inatel.projects.quotation.management.dto.StockDTO;
import br.inatel.projects.quotation.management.dto.StockManagerDTO;
import br.inatel.projects.quotation.management.exception.ExceptionCase;
import br.inatel.projects.quotation.management.model.Quote;
import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.repository.QuoteRepository;
import br.inatel.projects.quotation.management.repository.StockRepository;

@Service
@Transactional
public class QuotationService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private StockAdapter stockAdapter;

	public QuotationService(StockRepository stockRepository, QuoteRepository quoteRepository,
			StockAdapter stockManagerAdapter) {
		this.stockRepository = stockRepository;
		this.quoteRepository = quoteRepository;
		this.stockAdapter = stockManagerAdapter;
	}

	/**
	 * method that lists all quotes recorded in the bank
	 * 
	 * @return list of quotes in format DTO -- ok
	 */

	@Cacheable(value = "stocksList")
	public List<Quote> listAllQuotes() {
		return quoteRepository.findAll();
	}

	/**
	 * method that lists all the shares registered in the bank
	 * 
	 * @return stock list -- ok
	 */

	@Cacheable(value = "stocksList")
	public List<Stock> listAllStock() {
		List<Stock> stocks = stockRepository.findAll();
		stocks.forEach(s -> s.getQuotes().size());
		return stocks;
	}

	/**
	 * method that fetches a stockId by id
	 * 
	 * @return stockId
	 */

	@Cacheable(value = "stocksList")
	public Optional<Stock> findByIdStock(String id) {
		return stockRepository.findById(id);
	}

	/**
	 * 
	 * method that fetches a stock by id
	 * 
	 * @return stockId
	 */

	@Cacheable(value = "stocksList")
	public Optional<Quote> findById(String id) {
		return quoteRepository.findById(id);
	}

	/**
	 * method that fetches stock and its quote list by stockId as per its associated
	 * quote list
	 * 
	 * @return a quote list -- ok
	 */

	@Cacheable(value = "stocksList")
	public List<Quote> findByStockId(String idStock) {
		return quoteRepository.findByStockId(idStock);
	}

	/**
	 * method that registers a quote
	 * 
	 * @return quote created
	 * @throws ExceptionCase BadRequest -- ok
	 */

	@CacheEvict(value = "stocksList", allEntries = true)
	public Quote insertQuotation(QuoteDTO quoteDTO) throws ExceptionCase {

		Quote qm = new Quote();
		qm.setDataQuote(quoteDTO.getDataQuote());
		qm.setQuotePrice(quoteDTO.getQuotePrice());

		Optional<Stock> ac = stockRepository.findById(quoteDTO.getStockId());

		if (ac != null && ac.isPresent()) {
			qm.setStock(ac.get());
		} else {
			Stock stockCreated = existsAtStockManager(quoteDTO.getStockId());
			if (stockCreated != null) {
				qm.setStock(stockCreated);
			}
		}

		return quoteRepository.save(qm);
	}

	/**
	 * method that deletes a quote by id
	 * 
	 * @param quoteId
	 * @return error or success message - ok
	 * @throws ExceptionCase BadRequest -- ok
	 */

	@CacheEvict(value = "stocksList", allEntries = true)
	public String deleteQuotation(String quoteId) {

		Optional<?> qtOptional = findById(quoteId);

		if (qtOptional != null && qtOptional.isPresent()) {
			quoteRepository.deleteById(quoteId);
			return "successfully deleted";
		}

		return "quote not found";
	}

	/**
	 * method that changes a quote
	 * 
	 * @params QuoteDTO quoteDTO, String quoteId
	 * @return QuoteDTO changed quote - ok
	 * @throws ExceptionCase BadRequest -- ok
	 */

	@CacheEvict(value = "stocksList", allEntries = true)
	public Quote updateQuotation(QuoteDTO quoteDTO, String quoteId) throws ExceptionCase {

		Optional<Quote> qtOptional = findById(quoteId);

		Quote qtSalvo = null;

		if (qtOptional != null && qtOptional.isPresent()) {
			Quote qt = qtOptional.get();
			qt.setDataQuote(quoteDTO.getDataQuote());
			qt.setQuotePrice(quoteDTO.getQuotePrice());

			Optional<Stock> ac = stockRepository.findById(quoteDTO.getStockId());

			if (ac != null && ac.isPresent()) {
				qt.setStock(ac.get());
			} else {
				Stock stockCriado = existsAtStockManager(quoteDTO.getStockId());
				if (stockCriado != null) {
					qt.setStock(stockCriado);
				}
			}

			qtSalvo = quoteRepository.save(qt);
		}

		return qtSalvo;

	}

	/**
	 * method that registers multiple quotes
	 * 
	 * @return quotes created
	 * @throws ExceptionCase BadRequest -- ok
	 */
	@CacheEvict(value = "stocksList", allEntries = true)
	public Stock insertMoreQuotation(StockDTO stockDTO) {

		Optional<Stock> st = stockRepository.findById(stockDTO.getStockId());
		Stock stock = null;
		if (st != null && st.isPresent()) {
			stock = st.get();
		} else {
			stock = existsAtStockManager(stockDTO.getStockId());
		}

		if (stockDTO.getQuotes() != null && !stockDTO.getQuotes().isEmpty()) {

			for (QuoteDTO quote : stockDTO.getQuotes()) {
				if (stock != null) {
					quote.setStockId(stock.getId());
					stock.addQuote(insertQuotation(quote));
				}
			}
		}

		return stock;
	}

	/**
	 * method that saves a stock
	 * 
	 * @param stock
	 * @return new stock
	 * @throws ExceptionCase BadRequest -- ok
	 */
	@CacheEvict(value = "stocksList", allEntries = true)
	public Stock save(Stock stock) {
		return stockRepository.save(stock);
	}

	public Stock existsAtStockManager(String stockId) {
		List<StockManagerDTO> stocksAtManager = stockAdapter.listAll();

		StockManagerDTO stockManager = null;
		for (StockManagerDTO item : stocksAtManager) {
			if (item.contains(stockId)) {
				stockManager = item;
				break;
			}
		}

		Stock newStock = null;
		if (stockManager != null) {
			newStock = new Stock();
			newStock.setId(stockManager.getId());
			newStock.setDescription(stockManager.getDescription());
			newStock = save(newStock);
		} else {
			throw new ExceptionCase("the reported stock does not exist in the manager");
		}

		return newStock;
	}

	public List<QuoteDTO> buildListQuotes(List<Quote> quotes) {
		List<QuoteDTO> quotesDTO = new ArrayList<>();
		for (Quote quote : quotes) {
			QuoteDTO qt = new QuoteDTO(quote);
			quotesDTO.add(qt);
		}
		return quotesDTO;
	}

}
