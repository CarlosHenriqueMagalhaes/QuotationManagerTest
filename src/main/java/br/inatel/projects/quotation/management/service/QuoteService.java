package br.inatel.projects.quotation.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.inatel.projects.quotation.management.dto.QuoteDTO;
import br.inatel.projects.quotation.management.dto.StockDTO;
import br.inatel.projects.quotation.management.exception.ExceptionCase;
import br.inatel.projects.quotation.management.model.Quote;
import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.repository.QuoteRepository;

@Service
@Transactional
public class QuoteService {

	//@Autowired
	private StockService stockService;

	//@Autowired
	private QuoteRepository quoteRepository;

	public QuoteService(@Lazy StockService stockService, QuoteRepository quoteRepository) {
		this.stockService = stockService;
		this.quoteRepository = quoteRepository;
	}

	/**
	 * método que lista todas as cotações gravadas no banco
	 * 
	 * @return lista de cotações em formato DTO -- ok
	 */

	public List<Quote> listAllQuotes() {
		return quoteRepository.findAll();
	}

	/**
	 * método que lista todas as ações cadastradas no banco
	 * 
	 * @return lista de ações -- verificar pq o jpa não esta incializando a lista de
	 *         cotas
	 */

	public List<Stock> listAllStock() {
		List<Stock> stock = stockService.listAllStock();
		return stock;
	}

	/**
	 * método que busca uma cotação por id
	 * 
	 * @return uma cotação por id - ok
	 */

	public Optional<Quote> findById(String quoteId) {
		return quoteRepository.findById(quoteId);
	}

	/**
	 * método que busca o stock e a sua lista de cotações por stockId -> conforme
	 * sua lista de cotações associadas
	 * 
	 * @return uma lista de cotações -- ok
	 */

	public List<Quote> findByStockId(String idStock) {
		return quoteRepository.findByStockId(idStock);
	}

	/**
	 * 
	 * 
	 * @return  -- ok
	 */
	public Stock findByStock(String idStock) {
		Stock ac = stockService.findByStockId(idStock);
		return ac;
	}

	/**
	 * método que cadastra uma cotação
	 * 
	 * @return cotação criada
	 * @throws ExceptionCase BadRequest -- ok
	 */

	public Quote insertQuotation(QuoteDTO quoteDTO) throws ExceptionCase {

		Quote qm = new Quote();
		qm.setDate(quoteDTO.getDate());
		qm.setValor(quoteDTO.getValor());

		// inserir a cheve estrangeira que faz o vínvulo vom a ação
		// fazer a veridicaçãos e existe a ação para fazer o vínculo e se existir setar
		// no campo da quotação
		Stock ac = stockService.findByStockId(quoteDTO.getStockId());

		if (ac != null) {
			qm.setStock(ac);
		} else {
			throw new ExceptionCase("error when registering");
		}

		return quoteRepository.save(qm);
	}

	/**
	 * deleta uma cotação por id
	 * 
	 * @param quoteId
	 * @return mensagem de erro ou sucesso - ok
	 */

	public String deleteQuotation(String quoteId) {

		Optional<Quote> qtOptional = findById(quoteId);

		if (qtOptional != null && qtOptional.isPresent()) {
			quoteRepository.deleteById(quoteId);
			return "successfully deleted";
		}

		return "quote not found";
	}

	/**
	 * método que altera uma cotação
	 * 
	 * @params QuoteDTO quoteDTO, String quoteId
	 * @return QuoteDTO cotação alterada - ok
	 */

	public Quote updateQuotation(QuoteDTO quoteDTO, String quoteId) throws ExceptionCase {

		Optional<Quote> qtOptional = findById(quoteId);

		Quote qtSalvo = null;

		// aqui eu faço a alteração conforme o que o usuário digitou
		if (qtOptional != null && qtOptional.isPresent()) {
			Quote qt = qtOptional.get(); // pego o elemento/objeto que foi retornado
			qt.setDate(quoteDTO.getDate());
			qt.setValor(quoteDTO.getValor());

			// verifica se existe a ação informada para setar na cotação
			Stock ac = stockService.findByStockId(quoteDTO.getStockId());

			if (ac != null) {
				qt.setStock(ac);
			} else {
				throw new ExceptionCase("action not found!");
			}

			qtSalvo = quoteRepository.save(qt);
		}

		return qtSalvo;

	}

	/**
	 * método que cadastra uma cotação
	 * 
	 * @return cotação criada
	 * @throws ExceptionCase BadRequest -- ok
	 */

	public Stock insertMoreQuotation(StockDTO actionDTO) {
		Stock ac = new Stock();
		ac.setStockId(actionDTO.getStockId());
		if (actionDTO.getQuotes() != null && !actionDTO.getQuotes().isEmpty()) {

//			esse faz para quando só passar o actionId em cima e não em cada cotação
			for (QuoteDTO quotes : actionDTO.getQuotes()) {
				quotes.setStockId(actionDTO.getStockId());
				ac.addQuote(insertQuotation(quotes));
			}

//			esse considera que para cada cotação enviada na lista é necessário passar o stockid
//			actionDTO.getQuotes().stream().forEach(n -> ac.addQuote(insertQuotation(n)));
		}

		return ac;
	}

}
