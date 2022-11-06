package br.inatel.projects.quotation.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.projects.quotation.management.model.ActionModel;

public interface ActionRepository extends JpaRepository<ActionModel, String> {

	ActionModel findByStockId(String stockId);

}
