<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tags:pageTemplate
	titulo="Livros de Java, Android, iPhone, Ruby, PHP e muito mais ....">

	<section id="index-section" class="container middle">
		<div class="container">
			<h1>Cadastro de Permissões para ${usuario.nome}</h1>
			<form:form action="${s:mvcUrl('UC#editarRoles').build() }"
				method="post" commandName="usuario" enctype="multipart/form-data">
				<div class="form-group">
					<label>Permissoes</label>
					<form:checkboxes items="${availableRoles}" path="roles" />
					<form:errors path="roles" cssClass="error" />
				</div>
				<div class="form-group">
					<form:hidden path="email" />
				</div>
				<button type="submit" class="btn btn-primary">Atualizar</button>
			</form:form>
		</div>
	</section>
</tags:pageTemplate>
