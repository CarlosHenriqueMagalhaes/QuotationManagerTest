package br.inatel.projects.quotation.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.inatel.projects.quotation.management.dto.ActionDTO;
import br.inatel.projects.quotation.management.dto.QuoteDTO;
import br.inatel.projects.quotation.management.exception.ExceptionCase;
import br.inatel.projects.quotation.management.model.ActionModel;
import br.inatel.projects.quotation.management.model.QuoteModel;
import br.inatel.projects.quotation.management.repository.QuoteRepository;

@Service
@Transactional
public class QuoteService {

	@Autowired
	private ActionService actionService;

	@Autowired
	private QuoteRepository quoteRepository;

	public QuoteService(@Lazy ActionService actionService, QuoteRepository quoteRepository) {
		this.actionService = actionService;
		this.quoteRepository = quoteRepository;
	}

	public List<ActionModel> listAllActions() {
		List<ActionModel> stock = actionService.listAllAction();
		return stock;
	}

	public List<QuoteModel> listAllQuotes() {
		return quoteRepository.findAll();
	}

	public Optional<QuoteModel> findById(String quoteId) {
		return quoteRepository.findById(quoteId);
	}

	public List<QuoteModel> findByStockId(String idStock) {
		return quoteRepository.findByStockId(idStock);
	}

	public ActionModel findByActionId(String idStock) {
		ActionModel ac = actionService.findByActionId(idStock);
		return ac;
	}

	public QuoteModel insertQuotation(QuoteDTO quoteDTO) throws ExceptionCase {

		QuoteModel qm = new QuoteModel();
		qm.setDate(quoteDTO.getDate());
		qm.setValor(quoteDTO.getValor());

		// inserir a cheve estrangeira que faz o vínvulo vom a ação

		// fazer a veridicaçãos e existe a ação para fazer o vínculo e se existir setar
		// no campo da quotação
		ActionModel ac = actionService.findByActionId(quoteDTO.getStockId());

		if (ac != null) {
			qm.setStock(ac);
		} else {
			throw new ExceptionCase("Erro ao cadastrar");
		}

		return quoteRepository.save(qm);

	}

	public QuoteModel updateQuotation(QuoteDTO quoteDTO, String quoteId) throws ExceptionCase {

		Optional<QuoteModel> qtOptional = findById(quoteId);

		QuoteModel qtSalvo = null;

		// aqui eu faço a alteração conforme o que o usuário digitou
		if (qtOptional != null && qtOptional.isPresent()) {
			QuoteModel qt = qtOptional.get(); // pego o elemento/objeto que foi retornado
			qt.setDate(quoteDTO.getDate());
			qt.setValor(quoteDTO.getValor());

			// verifica se existe a ação informads para setar na cotação
			ActionModel ac = actionService.findByActionId(quoteDTO.getStockId());

			if (ac != null) {
				qt.setStock(ac);
			} else {
				throw new ExceptionCase("Ação não encontrada!");
			}

			qtSalvo = quoteRepository.save(qt);
		}

		return qtSalvo;

	}

	public String deleteQuotation(String quoteId) {

		Optional<QuoteModel> qtOptional = findById(quoteId);

		if (qtOptional != null && qtOptional.isPresent()) {
			quoteRepository.deleteById(quoteId);
			return "deletado com sucesso";
		}

		return "cotação não encontrada";
	}

	public ActionModel insertMoreQuotation(ActionDTO actionDTO) {
		ActionModel ac = new ActionModel();
		ac.setStockId(actionDTO.getStockId());
		if (actionDTO.getQuotes() != null && !actionDTO.getQuotes().isEmpty()) {
			
			//esse faz para qdo só passar o actionId em cima e não em cada cotação
			for(QuoteDTO quotes : actionDTO.getQuotes()) {
				quotes.setStockId(actionDTO.getStockId());
				ac.addQuote(insertQuotation(quotes));
			}
			
			//esse considera que para cada cotação enviada na lista é necessário passar o stockid
//			actionDTO.getQuotes().stream().forEach(n -> ac.addQuote(insertQuotation(n)));
		}

		return ac;
	}

}
