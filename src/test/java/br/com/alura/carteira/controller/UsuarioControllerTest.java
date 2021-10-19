package br.com.alura.carteira.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc
		.perform(
				post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isBadRequest());

		
	}
	
	@Test
	void DeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
		String json = "{\"nome\":\"fulano\",\"login\":\"fulano@fulano.com\"}";
		
		mvc
		.perform(
				post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(content().json(json));

		
	}

}