package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.models.UsuarioCriacao;
import br.com.casadocodigo.loja.validation.UsuarioCriacaoValidation;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioDAO dao;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UsuarioController(UsuarioDAO dao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioCriacaoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(UsuarioCriacao usuario) {
		return new ModelAndView("usuarios/form");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Usuario> usuarios = dao.listar();
		ModelAndView modelAndView = new ModelAndView("usuarios/lista");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value = "produtosHome", allEntries = true)
	public ModelAndView gravar(@Valid UsuarioCriacao usuarioCriacao, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors() || usuarioExiste(usuarioCriacao, result)) {
			return form(usuarioCriacao);
		}

		dao.gravar(mapUsuarioCriacaoToUsuario(usuarioCriacao));

		redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");

		return new ModelAndView("redirect:/usuarios");
	}

	private boolean usuarioExiste(UsuarioCriacao usuarioCriacao, BindingResult result) {
		Usuario usuario = dao.findByEmail(usuarioCriacao.getEmail());
		if (usuario != null) {
			result.rejectValue("email", "field.invalid");
			return true;
		}
		return false;
	}

	private Usuario mapUsuarioCriacaoToUsuario(UsuarioCriacao usuarioCriacao) {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setEmail(usuarioCriacao.getEmail());
		novoUsuario.setNome(usuarioCriacao.getNome());
		novoUsuario.setSenha(passwordEncoder.encode(usuarioCriacao.getSenha()));
		return novoUsuario;
	}

}
