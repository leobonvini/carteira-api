package br.com.alura.carteira.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoTransacaoFormDTO extends TransacaoFormDTO {

	@NotNull
	private Long id;

}
