package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.UsuarioCriacao;

public class UsuarioCriacaoValidation implements Validator {

	/** Tamanho minimo da senha */
	private static final int MIN_TAM_SENHA = 5;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioCriacao.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "email", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senha", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senhaRepetida", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "nome", "field.required");

		UsuarioCriacao usuario = (UsuarioCriacao) target;
		if (usuario.getSenha().length() != MIN_TAM_SENHA) {
			errors.rejectValue("senha", "field.invalid.size", new Object[] { MIN_TAM_SENHA }, "");
		}
		if (!usuario.getSenha().equals(usuario.getSenhaRepetida())) {
			errors.rejectValue("senha", "field.invalid");
		}
	}

}
