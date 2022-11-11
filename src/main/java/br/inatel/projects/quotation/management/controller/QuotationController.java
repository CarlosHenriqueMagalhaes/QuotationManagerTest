package br.inatel.projects.quotation.management.controller;

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
import br.inatel.projects.quotation.management.service.QuoteService;

@RestController
@RequestMapping("/quotations")
public class QuotationController {

	@Autowired
	private QuoteService quoteService;

	public QuotationController(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	/**
	 * método que busca todas as cotações gravadas no banco
	 * 
	 * @return lista de cotações em formato DTO -- ok
	 */

	@GetMapping
	public ResponseEntity<?> listQuotes() {
		List<Quote> quotes = quoteService.listAllQuotes();
		return ResponseEntity.ok().body(quotes.stream().map(QuoteDTO::new));
	}

	/**
	 * busca todas as ações cadastradas no banco
	 * 
	 * @return lista de ações -- verificar pq o jpa não esta incializando a lista de
	 *         cotas
	 */
	@GetMapping("/all/stocks")
	public ResponseEntity<?> listAllStock() {
		List<Stock> stocklist = quoteService.listAllStock();
		return ResponseEntity.ok().body(stocklist.stream().map(StockDTO::new));
	}

	/**
	 * método que busca uma cotação por id
	 * 
	 * @return uma cotação por id - ok
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
	 * método que busca o stock e a sua lista de cotações por stockId -> conforme
	 * sua lista de cotações associadas
	 * 
	 * @return uma lista de cotações -- ok
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
	 * método que cadastra uma cotação
	 * 
	 * @return cotação criada
	 * @throws ExceptionCase BadRequest -- ok
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
	 * deleta uma cotação por id
	 * 
	 * @param quoteId
	 * @return mensagem de erro ou sucesso - ok
	 */
	@DeleteMapping("/delete/{quoteId}")
	public ResponseEntity<?> deleteQuotation(@PathVariable String quoteId) {
		return ResponseEntity.ok().body(quoteService.deleteQuotation(quoteId));
	}

	/**
	 * método que altera uma cotação
	 * 
	 * @params QuoteDTO quoteDTO, String quoteId
	 * @return QuoteDTO cotação alterada - ok
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
	 * método que cadastra uma cotação
	 * 
	 * @return cotação criada
	 * @throws ExceptionCase BadRequest -- ok
	 */
	@PostMapping("/insertAll")
	public ResponseEntity<?> insertMoreQuotations(@RequestBody StockDTO actionDTO) throws ExceptionCase {
		try {
			Stock action = quoteService.insertMoreQuotation(actionDTO);
			StockDTO dto = new StockDTO(action);
			return ResponseEntity.ok().body(dto);
		} catch (ExceptionCase e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

}