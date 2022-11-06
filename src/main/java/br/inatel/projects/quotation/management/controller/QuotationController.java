package br.inatel.projects.quotation.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.projects.quotation.management.dto.ActionDTO;
import br.inatel.projects.quotation.management.dto.QuoteDTO;
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
	 * método que busca todas as cotações garavadas no banco
	 * 
	 * @return lista de cotações
	 */
	@GetMapping
	public List<QuoteModel> listQuotes() {
		return quoteService.listAllQuotes();
	}

	/**
	 * busca todas as ações cadastradas no banco
	 * 
	 * @return lista de ações
	 */
	@GetMapping("/all/actions")
	public List<ActionModel> listAllActions() {
		List<ActionModel> actionlist = quoteService.listAllActions();
		return actionlist;
	}

	/**
	 * método que busca uma cotação por id
	 * 
	 * @return uma cotação por id
	 */
	@GetMapping("/{idQuote}")
	public ResponseEntity<?> findById(@PathVariable String idQuote) {
		return ResponseEntity.ok().body(quoteService.findById(idQuote));
	}

	/**
	 * método que busca uma lista de cotações por stockId -> conforme sua lista de
	 * cotações associadas
	 * 
	 * @return uma lista de cotações
	 */
//	@GetMapping("/stock/{idStock}")
//	public List<QuoteModel> findByStockId(@PathVariable String idStock) {
//		return quoteService.findByStockId(idStock);
//	}

	/**
	 * método que busca o stock e a sua lista de cotações por stockId -> conforme
	 * sua lista de cotações associadas
	 * 
	 * @return uma lista de cotações
	 */
	@GetMapping("/stock/{idStock}")
	public ResponseEntity<?> findQuoteByStock(@PathVariable String idStock) {
		ActionModel ac = quoteService.findByActionId(idStock);
		List<QuoteModel> quotes = quoteService.findByStockId(ac.getId());
		ActionDTO dto = new ActionDTO(ac, quotes);
		return ResponseEntity.ok().body(dto);
	}

	/**
	 * método quecadastra uma cotação
	 * 
	 * @return cotação criada
	 * @throws NotFoundException
	 */
	@PostMapping("/insert")
	public ResponseEntity<?> insertQuotation(@RequestBody QuoteDTO quoteDTO) throws NotFoundException {
		QuoteModel quote = quoteService.insertQuotation(quoteDTO);
		QuoteDTO dto = new QuoteDTO(quote);
		return ResponseEntity.ok().body(dto);
	}

	/**
	 * método que altera uma cotação
	 * 
	 * @params QuoteDTO quoteDTO, String quoteId
	 * @return QuoteDTO cotação alterada
	 */
	@PutMapping("/update/{quoteId}")
	public ResponseEntity<?> updateQuotation(@RequestBody QuoteDTO quoteDTO, @PathVariable String quoteId) {
		QuoteModel quote = quoteService.updateQuotation(quoteDTO, quoteId);
		QuoteDTO dto = new QuoteDTO(quote);
		return ResponseEntity.ok().body(dto);
	}

	/**
	 * método que deleta uma cotação
	 * @params String quoteId
	 * @return mensagem de erro ou sucesso
	 */
	@DeleteMapping("/delete/{quoteId}")
	public ResponseEntity<?> deleteQuotation(@PathVariable String quoteId) {
		return ResponseEntity.ok().body(quoteService.deleteQuotation(quoteId));
	}

}
