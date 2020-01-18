<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tags:pageTemplate
	titulo="Livros de Java, Android, iPhone, Ruby, PHP e muito mais ....">


	<section id="index-section" class="container middle">
		<div class="container">
			<h1>Lista de Pedidos atuais</h1>
			<p>${sucesso}</p>
			<p>${falha}</p>

			<table class="table table-bordered table-striped table-hover">
				<tr>
					<th>ID</th>
					<th>Valor</th>
					<th>Data Pedido</th>
					<th>Títulos</th>
				</tr>
				<c:forEach items="${pedidos }" var="pedido">
					<tr>
						<td>${pedido.id }</td>
						<td><fmt:formatNumber pattern="0.00" type="currency"
								value="${pedido.valor }" /></td>
						<td>${pedido.data }</td>
						<td>${pedido.titulosProdutos }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</tags:pageTemplate>