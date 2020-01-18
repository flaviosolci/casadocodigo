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
			<a href="${s:mvcUrl('UC#form').build()}">Novo usuário</a>
			<h1>Lista de Usuários</h1>
			<p>${sucesso}</p>
			<p>${falha}</p>

			<table class="table table-bordered table-striped table-hover">
				<tr>
					<th>Nome</th>
					<th>Email</th>
					<th>Roles</th>
				</tr>
				<c:forEach items="${usuarios }" var="usuario">
					<tr>
						<td>${usuario.nome }</td>
						<td>${usuario.email }</td>
						<td>${usuario.roles }</td>
						<td><a
							href="${s:mvcUrl('UC#listarRoles').arg(0, usuario.email).build() }"><img
								src="<c:url value="/resources/imagens/adicionar.png" />"
								alt="editar" /></a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</tags:pageTemplate>