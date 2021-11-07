package br.com.alura.carteira.service;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.carteira.dto.AtualizacaoTransacaoFormDTO;
import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoDetalhadaDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CalculadoraDeImpostoService calculadoraDeImpostoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<TransacaoDTO> listar(Pageable paginacao, Usuario usuario){
		return transacaoRepository
				.findAllByUsuario(paginacao, usuario)
				.map(t -> modelMapper.map(t, TransacaoDTO.class));
		
//		List<TransacaoDTO> transacoesDTO = new ArrayList<>();
//		transacoes.forEach(transacao -> {
//			BigDecimal imposto = calculadoraDeImpostoService.calcular(transacao);
//			TransacaoDTO dto = modelMapper.map(transacao, TransacaoDTO.class);
//			dto.setImposto(imposto);
//			transacoesDTO.add(dto);
//			});
//		
//		return new PageImpl<TransacaoDTO>(
//				transacoesDTO, 
//				transacoes.getPageable(), 
//				transacoes.getTotalElements());
//		
	}

	@Transactional
	public TransacaoDTO cadastrar(TransacaoFormDTO dto, Usuario logado) {
		Long idUsuario = dto.getUsuarioId();
		
		try {
			Usuario usuario = usuarioRepository.getById(idUsuario);
			
			if(!usuario.equals(logado)) {
				lancarErroAcessoNegado();
			}
			
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(usuario);
			BigDecimal imposto = calculadoraDeImpostoService.calcular(transacao);
			transacao.setImposto(imposto);
			
			transacaoRepository.save(transacao);
			
			return modelMapper.map(transacao, TransacaoDTO.class);
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("usuario inexistente!");
		}
	}

	@Transactional
	public TransacaoDTO atualizar(AtualizacaoTransacaoFormDTO dto, Usuario logado) {
		Transacao transacao = transacaoRepository.getById(dto.getId());
		
		if(!transacao.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		transacao.atualizarInformacaoes(
				dto.getTicker(), 
				dto.getPreco(), 
				dto.getQuantidade(),
				dto.getData(), 
				dto.getTipo());
		
		return modelMapper.map(transacao, TransacaoDTO.class);
	}

	@Transactional
	public void remover(@NotNull Long id, Usuario logado) {
		Transacao transacao = transacaoRepository.getById(id);
		
		if(!transacao.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		transacaoRepository.deleteById(id);
		
	}

	public TransacaoDetalhadaDTO detalhar(Long id, Usuario logado) {
				
		Transacao transacao = transacaoRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		if(!transacao.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		return modelMapper.map(transacao, TransacaoDetalhadaDTO.class);
	}
	
	private void lancarErroAcessoNegado() {
		throw new AccessDeniedException("Acesso negado!");
	}
	
}

















