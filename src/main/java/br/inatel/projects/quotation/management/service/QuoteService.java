package br.inatel.projects.quotation.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.inatel.projects.quotation.management.dto.QuoteDTO;
import br.inatel.projects.quotation.management.model.ActionModel;
import br.inatel.projects.quotation.management.model.QuoteModel;
import br.inatel.projects.quotation.management.repository.QuoteRepository;

@Transactional
@Service
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

	public QuoteModel insertQuotation(QuoteDTO quoteDTO) throws NotFoundException {

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
			throw new NotFoundException();
		}

		return quoteRepository.save(qm);

	}

	public QuoteModel updateQuotation(QuoteDTO quoteDTO, String quoteId) {

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
//				throw new NotFoundException("Ação não encontrada!");
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

}
