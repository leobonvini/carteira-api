package br.com.alura.carteira.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFormDTO {

	@NotBlank
	private String login;
	
	@NotBlank
	private String senha;
}
