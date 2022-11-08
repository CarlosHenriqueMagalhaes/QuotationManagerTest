package br.inatel.projects.quotation.management.controller;

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

import br.inatel.projects.quotation.management.dto.ActionDTO;
import br.inatel.projects.quotation.management.dto.QuoteDTO;
import br.inatel.projects.quotation.management.exception.ExceptionCase;
import br.inatel.projects.quotation.management.model.ActionModel;
import br.inatel.projects.quotation.management.model.QuoteModel;
import br.inatel.projects.quotation.management.service.QuoteService;

@RestController // para indicar que é o controller
@RequestMapping("/quotations") // para mapear (link a ser usado no navegador)
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
		List<QuoteModel> quotes = quoteService.listAllQuotes();
		return ResponseEntity.ok().body(quotes.stream().map(QuoteDTO::new));
	}

	/**
	 * busca todas as ações cadastradas no banco
	 * 
	 * @return lista de ações -- verificar pq o jpa não esta incializando a lista de
	 *         cotas
	 */
	@GetMapping("/all/actions")
	public ResponseEntity<?> listAllActions() {
		List<ActionModel> actionlist = quoteService.listAllActions();
		return ResponseEntity.ok().body(actionlist.stream().map(ActionDTO::new));
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

	/**
	 * método que busca o stock e a sua lista de cotações por stockId -> conforme
	 * sua lista de cotações associadas
	 * 
	 * @return uma lista de cotações -- ok
	 */
	@GetMapping("/stock/{idStock}")
	public ResponseEntity<?> findQuoteByStock(@PathVariable String idStock) {
		ActionModel ac = quoteService.findByActionId(idStock);
		List<QuoteModel> quotes = quoteService.findByStockId(ac.getId());
		ActionDTO dto = new ActionDTO(ac, quotes);
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
			QuoteModel quote = quoteService.insertQuotation(quoteDTO);
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
	 * @return QuoteDTO cotação alterada  - ok 
	 */
	@PutMapping("/update/{quoteId}")
	public ResponseEntity<?> updateQuotation(@RequestBody QuoteDTO quoteDTO, @PathVariable String quoteId) {
		try {
			QuoteModel quote = quoteService.updateQuotation(quoteDTO, quoteId);
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
	public ResponseEntity<?> insertMoreQuotations(@RequestBody ActionDTO actionDTO) throws ExceptionCase {
		try {
			ActionModel action = quoteService.insertMoreQuotation(actionDTO);
			ActionDTO dto = new ActionDTO(action);
			return ResponseEntity.ok().body(dto);
		} catch (ExceptionCase e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	

}