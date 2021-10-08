package br.com.alura.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.carteira.dto.ItemCarteiraDTO;
import br.com.alura.carteira.modelo.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query("select new br.com.alura.carteira.dto.ItemCarteiraDTO("
			+ "t.ticker, "
			+ "sum(t.quantidade), "
			+ "sum(t.quantidade) * 1.0/(select sum(t2.quantidade) from Transacao t2) * 1.0)"
			+ " from Transacao t "
			+ "group by t.ticker")
	List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos();

}