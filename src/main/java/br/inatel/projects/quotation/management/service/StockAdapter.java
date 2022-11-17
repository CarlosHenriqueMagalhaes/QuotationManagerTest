package br.inatel.projects.quotation.management.service;

/**
 * Represents the adaptation with api
 * @author carlos.magalhaes
 * @since 11/10/2022
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.projects.quotation.management.dto.StockManagerDTO;
import reactor.core.publisher.Flux;

@Service
public class StockAdapter {

	@Value("${URL_MANAGER}")
	private String URL_MANAGER;

	@Value("${server.host}")
	private String host;

	@Value("${quotation.port}")
	private String port;

	@Cacheable(value = "stocksAtManagerList")
	public List<StockManagerDTO> listAll() {

		List<StockManagerDTO> stocksAtManager = new ArrayList<>();

		Flux<StockManagerDTO> flux = WebClient.builder().baseUrl("http://" + URL_MANAGER).build().get().uri("/stock").retrieve()
				.bodyToFlux(StockManagerDTO.class);

		flux.subscribe(f -> stocksAtManager.add(f));
		flux.blockLast();

		return stocksAtManager;
	}
}
