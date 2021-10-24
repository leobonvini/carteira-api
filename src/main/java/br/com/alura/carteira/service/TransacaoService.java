package br.com.alura.carteira.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	ModelMapper modelMapper = new ModelMapper();
	
	public Page<TransacaoDTO> listar(Pageable paginacao){
		Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
		return transacoes.map(t -> modelMapper.map(t, TransacaoDTO.class));
	}

	@Transactional
	public TransacaoDTO cadastrar(TransacaoFormDTO dto) {
		Long idUsuario = dto.getUsuarioId();
		
		try {
			Usuario usuario = usuarioRepository.getById(idUsuario);
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(usuario);
			
			transacaoRepository.save(transacao);
			
			return modelMapper.map(transacao, TransacaoDTO.class);
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("usuario inexistente!");
		}
	}

	@Transactional
	public TransacaoDTO atualizar(AtualizacaoTransacaoFormDTO dto) {
		Transacao transacao = transacaoRepository.getById(dto.getId());
		
		transacao.atualizarInformacaoes(
				dto.getTicker(), 
				dto.getPreco(), 
				dto.getQuantidade(),
				dto.getData(), 
				dto.getTipo());
		
		return modelMapper.map(transacao, TransacaoDTO.class);
	}

	@Transactional
	public void remover(@NotNull Long id) {
		transacaoRepository.deleteById(id);
		
	}

	public TransacaoDetalhadaDTO detalhar(Long id) {
		Transacao transacao = transacaoRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(transacao, TransacaoDetalhadaDTO.class);
	}
}

















