package br.inatel.projects.quotation.management.service;

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

	@Value("${server.port}")
	private String port;

	@Cacheable(value = "stocksAtManagerList")
	public List<StockManagerDTO> listAll() {

		List<StockManagerDTO> stocksAtManager = new ArrayList<>();

		Flux<StockManagerDTO> flux = WebClient.create(URL_MANAGER).get().uri("/stock").retrieve()
				.bodyToFlux(StockManagerDTO.class);

		flux.subscribe(f -> stocksAtManager.add(f));
		flux.blockLast();

		return stocksAtManager;
	}
}
