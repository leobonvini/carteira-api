package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;


@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	private TransacaoService service;

	@Test
	void deveriaCadastrarUmaTransacao() {
		TransacaoFormDTO formDto = new TransacaoFormDTO(
				"ITSA4",
				new BigDecimal ("10.45"),
				120,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				1l
				);
		
		TransacaoDTO dto = service.cadastrar(formDto);
		
		Mockito.verify(transacaoRepository.save(Mockito.any()));
		
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
		
	}
	
	@Test
	void naoDeveriaCadastrarUmaTransacao() {
		TransacaoFormDTO formDto = new TransacaoFormDTO(
				"ITSA4",
				new BigDecimal ("10.45"),
				120,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				1l
				);
		
		Mockito.when(usuarioRepository.getById(formDto.getUsuarioId()))
		.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto));
		
	}

}
