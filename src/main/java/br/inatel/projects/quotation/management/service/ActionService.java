package br.inatel.projects.quotation.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.projects.quotation.management.model.ActionModel;
import br.inatel.projects.quotation.management.repository.ActionRepository;

@Transactional
@Service
public class ActionService {

	@Autowired
	private ActionRepository actionRepository;

	public ActionService(ActionRepository actionRepository) {
		this.actionRepository = actionRepository;
	}

	public List<ActionModel> listAllAction() {
		List<ActionModel> stock = actionRepository.findAll();
		return stock;
	}

	public Optional<ActionModel> findById(String id) {
		return actionRepository.findById(id);
	}

	public ActionModel findByActionId(String idStock) {
		ActionModel ac = actionRepository.findByStockId(idStock);
		return ac;
	}

}
