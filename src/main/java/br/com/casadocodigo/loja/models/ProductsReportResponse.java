package br.com.casadocodigo.loja.models;

import java.util.List;

/**
 * 
 * Model para o relatório dos produtos
 * 
 * @author Flavio Solci
 */
public class ProductsReportResponse {

	private Long dataGeracao;

	private int quatidade;

	private List<Produto> produtos;

	public ProductsReportResponse(List<Produto> produtos) {
		this.dataGeracao = System.currentTimeMillis();
		this.quatidade = produtos.size();
		this.produtos = produtos;
	}

	public Long getDataGeracao() {
		return dataGeracao;
	}

	public int getQuatidade() {
		return quatidade;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

}
