package br.inatel.projects.quotation.management;

/**
 * Unit tests for the Controller class
 * @author carlos.magalhaes
 * @since 11/16/2022
 */


import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.inatel.projects.quotation.management.dto.QuoteDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuotationControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	/**
	 * tests the GET method that lists all quotes
	 */
	// testa o método GET que lista todas as cotações
	@Test
	public void return200MethodListAllQuotes() {
		webTestClient.get().uri("/quotations").exchange().expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectStatus().isOk();
	}

//	@Test
//	void return200MethodListAllStock() {
//		 webTestClient.get()
//	        .uri("/quotations/all/stocks")
//	        .exchange()
//	        .expectHeader().contentType(MediaType.APPLICATION_JSON)
//	        .expectStatus().isOk();
//	}

	/**
	 *Tests the method that lists Stock with its quotes by quote id
	 */
	// Testa o método que mostra a cotação e o stock (ação) dessa cotação pelo id da cotação
	@Test
	public void return200MethodFindById() {
		webTestClient.get().uri("/quotations/0a261afe-3cdd-4777-a4fc-19952823aef0").exchange().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectStatus().isOk();
	}

	/**
	 * Tests the method that lists the Stock with its quotes
	 */
	// Testa o método que lista o Stock (ação) desejada e suas cotações através do stock
	@Test
	public void return200MethodFindQuoteByStockId() {
		webTestClient.get().uri("/quotations/stock/petr4").exchange().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectStatus().isOk().expectBody();
	}

	/**
	 *Test the POST method 
	 */
	// Testa o método POST
	@Test
	public void return200MethodPostQuotes() {
		QuoteDTO nq = createQuoteDTO();
		webTestClient.post().uri("/quotations/insert").body(BodyInserters.fromValue(nq)).exchange().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectStatus().isOk().expectBody();
	}

	public QuoteDTO createQuoteDTO() {
		QuoteDTO q = new QuoteDTO();
		q.setDataQuote(LocalDate.now());
		q.setQuotePrice(BigDecimal.ONE);
		q.setStockId("Petr4");
		return q;
	}

	/**
	 * Test the DELETE method
	 */
	// Testa o método DELETE
	@Test
	public void return200MethodDeleteOneQuoteByQuoteId() {
		webTestClient.delete().uri("/quotations/delete/1bb1ffc2-d981-4c25-91e4-56bddf14e373").exchange().expectHeader();
	}

//	@Test
//	public void return200MethodPutOneQuoteByQuoteIdToAnotherQuote() {
//		QuoteDTO nq = createQuoteDTO();
//		 webTestClient.put()
//	        .uri("/quotations/update/1bb1ffc2-d981-4c25-91e4-56bddf14e373")
//	        .body(BodyInserters.fromValue(nq))
//	        .exchange()
//	        .expectHeader().contentType(MediaType.APPLICATION_JSON)
//	        .expectStatus().isOk().expectBody();
//	}
//	

	/**
	 * Test the POST method that inserts multiple quotes
	 */
	// Testa o método POST que insere varias cotações
	@Test
	public void return200MethodPostMultipleQuotes() {
		QuoteDTO nq = createQuoteDTO();
		webTestClient.post().uri("/quotations/insertAll").body(BodyInserters.fromValue(nq)).exchange().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectStatus().isOk().expectBody();
	}

}
