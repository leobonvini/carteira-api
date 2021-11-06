package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;


@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private TransacaoService service;
	
	private Usuario logado;
	
	@BeforeEach
	public void before() {
		this.logado = new Usuario("Rodrigo", "rodrigo@email.com", "123456");
	}

	private TransacaoFormDTO criarTransacaoFormDTO() {
		TransacaoFormDTO formDTO = new TransacaoFormDTO(
				"ITSA4",
				new BigDecimal ("10.45"),
				120,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				1l
				);
		return formDTO;
	}
	
	@Test
	void deveriaCadastrarUmaTransacao() {
		TransacaoFormDTO formDTO = criarTransacaoFormDTO();
		
		Mockito.when(usuarioRepository.getById(formDTO.getUsuarioId())).thenReturn(logado);
		
		Transacao transacao= new Transacao(
				formDTO.getTicker(),
				formDTO.getPreco(),
				formDTO.getQuantidade(),
				formDTO.getData(),
				formDTO.getTipo(),
				logado);
		
		Mockito.when(modelMapper.map(formDTO, Transacao.class))
		.thenReturn(transacao);
		
		Mockito.when(modelMapper.map(transacao, TransacaoDTO.class))
		.thenReturn(new TransacaoDTO(
				null,
				transacao.getTicker(),
				transacao.getPreco(),
				transacao.getQuantidade(),
				transacao.getTipo()
				));
		
		TransacaoDTO dto = service.cadastrar(formDTO, logado);
		
		Mockito.verify(transacaoRepository).save(Mockito.any());
		
		assertEquals(formDTO.getTicker(), dto.getTicker());
		assertEquals(formDTO.getPreco(), dto.getPreco());
		assertEquals(formDTO.getQuantidade(), dto.getQuantidade());
		assertEquals(formDTO.getTipo(), dto.getTipo());
		
	}

	
	
	@Test
	void naoDeveriaCadastrarUmaTransacao() {
		TransacaoFormDTO formDto = criarTransacaoFormDTO();
		
		Mockito.when(usuarioRepository.getById(formDto.getUsuarioId()))
		.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto, logado));
		
	}

}
