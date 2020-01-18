package br.com.casadocodigo.loja.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.ProductsReportResponse;

@Controller
@RequestMapping("relatorio-produtos")
public class RelatorioProdutosController {

	private ProdutoDAO dao;


	@Autowired
	public RelatorioProdutosController(ProdutoDAO dao) {
		this.dao = dao;
	}

	@GetMapping
	@ResponseBody
	public ProductsReportResponse generateReport(@RequestParam(required = false) String data) throws ParseException {
		if (data == null) {
			System.out.println("==== Gerando relatório geral ====");
			return new ProductsReportResponse(dao.listar());

		}
		System.out.println("==== Gerando relatório para produtos a partir " + data);
		return new ProductsReportResponse(dao.listarAPartirDe(parseInputDate(data)));

	}

	/**
	 * Transforma a data recebida no formato String (yyyy-MM-dd) para Date. Se o
	 * formato nao estiver no padrão yyyy-MM-dd lanca uma exceção
	 * 
	 * @param data data de lancamento em formato String
	 * @return data transformada
	 * @throws ParseException
	 */
	private Date parseInputDate(String data) throws ParseException {
		Date dataDeLancamento;
		try {
			dataDeLancamento = new SimpleDateFormat("yyyy-MM-dd").parse(data);
		} catch (ParseException e) {
			System.err.println("=== Data inválida. Padrão esperado: yyyy-MM-dd");
			throw e;
		}
		return dataDeLancamento;
	}

}
