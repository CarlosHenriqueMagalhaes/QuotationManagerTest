package br.inatel.projects.quotation.management.controller;

/**
 * Controller class, where the endpoints will be made
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.projects.quotation.management.dto.QuoteDTO;
import br.inatel.projects.quotation.management.dto.StockDTO;
import br.inatel.projects.quotation.management.exception.ExceptionCase;
import br.inatel.projects.quotation.management.model.Quote;
import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.service.QuotationService;

@RestController
@RequestMapping("/quotations")
public class QuotationController {

	@Autowired
	private QuotationService quoteService;

	public QuotationController(QuotationService quoteService) {
		this.quoteService = quoteService;
	}

	/**
	 * method that mapping
	 * 
	 * @return quote list DTO -- ok
	 */

	@GetMapping
	public ResponseEntity<?> listQuotes() {
		List<Quote> quotes = quoteService.listAllQuotes();
		return ResponseEntity.ok().body(quotes.stream().map(QuoteDTO::new));
	}

	/**
	 * method that mapping
	 * 
	 * @return all stock lists
	 */
	@GetMapping("/all/stocks")
	public ResponseEntity<?> listAllStock() {
		List<Stock> stocklist = quoteService.listAllStock();
		return ResponseEntity.ok().body(stocklist.stream().map(StockDTO::new));
	}

	/**
	 * method that mapping
	 * 
	 * @return a quote per id - ok
	 */
	@GetMapping("/{idQuote}")
	public ResponseEntity<?> findById(@PathVariable String idQuote) {
		return ResponseEntity.ok().body(quoteService.findById(idQuote).map(QuoteDTO::new));
	}

//	@GetMapping("/action/{stockId}")
//	public ResponseEntity<?> findByStockId(@PathVariable String stockId) {
//		return ResponseEntity.ok().body(quoteService.findByStockId(stockId).stream().map(QuoteDTO::new));
//	}

	/**
	 * method that mapping
	 * 
	 * @return stock list -- ok
	 */
	@GetMapping("/stock/{idStock}")
	public ResponseEntity<?> findQuoteByStock(@PathVariable String idStock) {
		List<Quote> quotes = quoteService.findByStockId(idStock);
		List<QuoteDTO> quotesDTO = new ArrayList<>();
		for (Quote qt : quotes) {
			QuoteDTO qtDTO = new QuoteDTO(qt);
			quotesDTO.add(qtDTO);
		}
		StockDTO dto = new StockDTO(quotesDTO);
		return ResponseEntity.ok().body(dto);
	}

	/**
	 * method that mapping
	 * 
	 * @return quote created
	 */
	@PostMapping("/insert")
	public ResponseEntity<?> insertQuotation(@RequestBody QuoteDTO quoteDTO) throws ExceptionCase {
		try {
			Quote quote = quoteService.insertQuotation(quoteDTO);
			QuoteDTO dto = new QuoteDTO(quote);
			return ResponseEntity.ok().body(dto);
		} catch (ExceptionCase e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	/**
	 * method that mapping
	 * 
	 * @param quoteId
	 * @return error or success message - ok
	 */
	@DeleteMapping("/delete/{quoteId}")
	public ResponseEntity<?> deleteQuotation(@PathVariable String quoteId) {
		return ResponseEntity.ok().body(quoteService.deleteQuotation(quoteId));
	}

	/**
	 * method that mapping
	 * 
	 * @params QuoteDTO quoteDTO, String quoteId
	 * @return QuoteDTO changed quote - ok
	 */
	@PutMapping("/update/{quoteId}")
	public ResponseEntity<?> updateQuotation(@RequestBody QuoteDTO quoteDTO, @PathVariable String quoteId) {
		try {
			Quote quote = quoteService.updateQuotation(quoteDTO, quoteId);
			QuoteDTO dto = new QuoteDTO(quote);
			return ResponseEntity.ok().body(dto);
		} catch (ExceptionCase e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * method that mapping
	 * 
	 * @return multiple quotes created
	 */
	@PostMapping("/insertAll")
	public ResponseEntity<?> insertMoreQuotations(@RequestBody StockDTO stockDTO) throws ExceptionCase {
		try {
			Stock stock = quoteService.insertMoreQuotation(stockDTO);
			// seto todas as cotações existentes para essa ação na lista de cotações
			stock.setQuoteList(quoteService.findByStockId(stock.getId()));
			StockDTO dto = new StockDTO(stock);
			return ResponseEntity.ok().body(dto);
		} catch (ExceptionCase e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * metodo para cadastrar todos os stocks conforme estão vindo do manager 8080
	 * 
	 * @return multiple quotes created
	 */
	@PostMapping("/insertAllStocks")
	public ResponseEntity<?> insertAllStocks() throws ExceptionCase {
		try {
			return ResponseEntity.ok().body(quoteService.insertAllStocks());
		} catch (ExceptionCase e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}