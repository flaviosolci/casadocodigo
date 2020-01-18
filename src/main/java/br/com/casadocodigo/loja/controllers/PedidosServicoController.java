package br.com.casadocodigo.loja.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.ExternalOrderResponse;

@Controller
@RequestMapping("/pedidos")
public class PedidosServicoController {

	private RestTemplate restTemplate;

	@Autowired
	public PedidosServicoController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		String uri = "https://book-payment.herokuapp.com/orders";
		List<ExternalOrderResponse> response = Arrays
				.asList(restTemplate.getForObject(uri, ExternalOrderResponse[].class));
		ModelAndView modelAndView = new ModelAndView("pedidos/pedidos");
		modelAndView.addObject("pedidos", response);
		return modelAndView;
	}

}
