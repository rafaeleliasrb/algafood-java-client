package com.algaworks.algafood.client;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;
import com.algaworks.algafood.client.model.input.CidadeIdInput;
import com.algaworks.algafood.client.model.input.CozinhaIdInput;
import com.algaworks.algafood.client.model.input.EnderecoInput;
import com.algaworks.algafood.client.model.input.RestauranteInput;

public class InclusaoRestaurantesMain {

	public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			RestauranteClient restauranteClient = new RestauranteClient(restTemplate, "http://localhost:8080");
			

			CidadeIdInput cidade = new CidadeIdInput();
			cidade.setId(1l);
			
			EnderecoInput endereco = new EnderecoInput();
			endereco.setBairro("Joaquim Távora");
			endereco.setCidade(cidade);
			endereco.setCep("60110535");
			endereco.setLogradouro("Rua João Cordeiro");
			endereco.setNumero("3300");
			
			CozinhaIdInput cozinha = new CozinhaIdInput();
			cozinha.setId(1l);

			RestauranteInput restaurante = new RestauranteInput();
			restaurante.setNome("Dona Deôla");
			restaurante.setTaxaFrete(BigDecimal.valueOf(12l));
			restaurante.setCozinha(cozinha);
			restaurante.setEndereco(endereco);
			
			System.out.println(restauranteClient.adicionar(restaurante));
		} catch (ClientApiException e) {
			if(e.getProblem() != null) {
				System.out.println(e.getProblem());
				System.out.println(e.getProblem().getUserMessage());
				if(e.getProblem().getObjects() != null && !e.getProblem().getObjects().isEmpty()) {
					e.getProblem().getObjects().stream()
						.forEach(object -> System.out.println("- " + object.getUserMessage()));
				}
			}
			else {
				System.out.println("Erro desconhecido");
				e.printStackTrace();
			}
		}
		
	}
}
