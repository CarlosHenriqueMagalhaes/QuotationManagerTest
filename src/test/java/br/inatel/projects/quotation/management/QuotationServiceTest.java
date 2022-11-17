package br.inatel.projects.quotation.management;

/**
 * Unit tests for the Service class
 * @author carlos.magalhaes
 * @since 11/16/2022
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.inatel.projects.quotation.management.model.Quote;
import br.inatel.projects.quotation.management.model.Stock;
import br.inatel.projects.quotation.management.service.QuotationService;

@SpringBootTest
class QuotationServiceTest {

	@Autowired
	private QuotationService quotationService;

	/**
	 * tests whether there are quotes saved in the database
	 */
	// testa se existem cotações salvas no banco
	@Test
	public void ListOfQuotesIsNotEmpty() {
		List<Quote> quote = quotationService.listAllQuotes();
		assertNotNull(quote);
	}

	/**
	 * check if there are stocks in the bank
	 */
	// testa se existem stocks no banco
	@Test
	public void ListOfStocksIsNotEmpty() {
		List<Stock> stocks = quotationService.listAllStock();
		assertNotNull(stocks);
	}

	/**
	 * Tests for a valid ID
	 */
	//Testa um ID válido
	@Test
	public void ReturnAllQuoteByStockId() {
		String stockId = "petr4";
		Optional<Stock> opStock = quotationService.findByIdStock(stockId);
		assertNotNull(opStock);
		assertEquals(stockId, opStock.get().getId());
	}

	/**
	 * Tests for an invalid ID
	 */
	//Testa um ID inválido
	@Test
	public void ReturnStockQuoteByInvalidStockId() {
		String stockId = "TESTA";
		Optional<Stock> opStock = quotationService.findByIdStock(stockId);
		assertTrue(opStock.isEmpty());
	}

}