package br.inatel.projects.quotation.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
public class QuoteService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private StockAdapter stockAdapter;

	public QuoteService(StockRepository stockRepository, QuoteRepository quoteRepository,
			StockAdapter stockManagerAdapter) {
		this.stockRepository = stockRepository;
		this.quoteRepository = quoteRepository;
		this.stockAdapter = stockManagerAdapter;
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
		List<Stock> stocks = stockRepository.findAll();
		stocks.forEach(s -> s.getQuotes().size());
		return stocks;
	}

	/**
	 * método que busca uma stock por id
	 * 
	 * @return uma ação
	 */

	public Optional<Stock> findByIdStock(String id) {
		return stockRepository.findById(id);
	}

	/**
	 * método que busca uma stock por id
	 * 
	 * @return uma ação
	 */
	public Optional<Quote> findById(String id) {
		return quoteRepository.findById(id);
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
	 * método que cadastra uma cotação
	 * 
	 * @return cotação criada
	 * @throws ExceptionCase BadRequest -- ok
	 */
	public Quote insertQuotation(QuoteDTO quoteDTO) throws ExceptionCase {

		Quote qm = new Quote();
		qm.setDataQuote(quoteDTO.getDataQuote());
		qm.setQuotePrice(quoteDTO.getQuotePrice());

		// inserir a cheve estrangeira que faz o vínvulo vom a ação
		// fazer a veridicaçãos e existe a ação para fazer o vínculo e se existir setar
		// no campo da quotação

		Optional<Stock> ac = stockRepository.findById(quoteDTO.getStockId());

		if (ac != null && ac.isPresent()) {
			qm.setStock(ac.get());
		} else {
			Stock stockCriado = existsAtStockManager(quoteDTO.getStockId());
			if (stockCriado != null) {
				qm.setStock(stockCriado);
			}
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

		Optional<?> qtOptional = findById(quoteId);

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
			qt.setDataQuote(quoteDTO.getDataQuote());
			qt.setQuotePrice(quoteDTO.getQuotePrice());

			// verifica se existe a ação informada para setar na cotação
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
	 * método que cadastra várias cotação
	 * 
	 * @return cotação criada
	 * @throws ExceptionCase BadRequest -- ok
	 */

	public Stock insertMoreQuotation(StockDTO stockDTO) {

		// verifica se existe a ação informada para setar na cotação
		Optional<Stock> st = stockRepository.findById(stockDTO.getStockId());
		Stock stock = null;
		if (st != null && st.isPresent()) {
			stock = st.get();
		} else {
			stock = existsAtStockManager(stockDTO.getStockId());
		}

		if (stockDTO.getQuotes() != null && !stockDTO.getQuotes().isEmpty()) {
//			esse faz para quando só passar o actionId em cima e não em cada cotação
			for (QuoteDTO quote : stockDTO.getQuotes()) {
				if(stock != null) {
					quote.setStockId(stock.getId());					
					stock.addQuote(insertQuotation(quote));
				}
			}

//			esse considera que para cada cotação enviada na lista é necessário passar o stockid
//			actionDTO.getQuotes().stream().forEach(n -> ac.addQuote(insertQuotation(n)));
		}

		return stock;
	}

	/**
	 * salva um stock
	 * 
	 * @param stock
	 */
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
			throw new ExceptionCase("o estoque informado não existe no gerenciador");
		}

		return newStock;
	}

}
