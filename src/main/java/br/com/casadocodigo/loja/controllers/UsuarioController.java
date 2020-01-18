package br.com.casadocodigo.loja.controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.RoleDAO;
import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.models.UsuarioCriacao;
import br.com.casadocodigo.loja.validation.UsuarioCriacaoValidation;
import br.com.casadocodigo.loja.validation.UsuarioValidation;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioDAO dao;
	private PasswordEncoder passwordEncoder;
	private RoleDAO roleDAO;

	@Autowired
	public UsuarioController(UsuarioDAO dao, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;
		this.roleDAO = roleDAO;

	}

	@InitBinder("usuarioCriacao")
	public void initBinderUsuarioCriacao(WebDataBinder binder) {
		binder.addValidators(new UsuarioCriacaoValidation());
	}

	@InitBinder("Usuario")
	public void initBinderUsuario(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation());
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
	@CacheEvict(value = "usuariosHome", allEntries = true)
	public ModelAndView gravar(@Valid UsuarioCriacao usuario, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors() || usuarioExiste(usuario, result)) {
			return form(usuario);
		}

		dao.gravar(mapusUsuarioCriacaoToUsuario(usuario));

		redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");

		return new ModelAndView("redirect:/usuarios");
	}

	@PostMapping("/roles")
	@CacheEvict(value = "usuariosHome", allEntries = true)
	public ModelAndView editarRoles(@Valid Usuario usuarioMudado, RedirectAttributes redirectAttributes) {
		Usuario usuarioCadastrado = dao.findByEmail(usuarioMudado.getEmail());

		if (usuarioCadastrado == null) {
			throw new UsernameNotFoundException("Usuario " + usuarioMudado.getEmail() + " não foi encontrado");
		}

		usuarioCadastrado.setRoles(usuarioMudado.getRoles());
		dao.gravar(usuarioCadastrado);

		redirectAttributes.addFlashAttribute("sucesso", "Permissões alteradas com sucesso!");

		return new ModelAndView("redirect:/usuarios");
	}

	@GetMapping("/roles")
	public ModelAndView listarRoles(@RequestParam("email") String email) {
		ModelAndView modelAndView = new ModelAndView("/usuarios/form-roles");
		modelAndView.addObject("usuario", dao.loadUserByUsername(email));
		return modelAndView;

	}

	// ===================
	// == HELPERS
	// ===================

	@ModelAttribute("availableRoles")
	public Collection<Role> getWebFrameworkList() {
		return roleDAO.listar();

	}

	private boolean usuarioExiste(UsuarioCriacao usuario, BindingResult result) {
		if (dao.findByEmail(usuario.getEmail()) != null) {
			result.rejectValue("email", "field.invalid");
			return true;
		}
		return false;
	}

	private Usuario mapusUsuarioCriacaoToUsuario(UsuarioCriacao usuario) {
		Usuario novoUsuario = new Usuario();
		novoUsuario.setEmail(usuario.getEmail());
		novoUsuario.setNome(usuario.getNome());
		novoUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return novoUsuario;
	}

}
