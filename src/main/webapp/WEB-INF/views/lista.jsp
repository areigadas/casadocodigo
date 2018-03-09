<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!-- Import da taglib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Lista de Produtos</h1>
	<p> ${sucesso} </p>
    <table>
        <tr>
            <td>Título</td>
            <td>Descrição</td>
            <td>Páginas</td>
            <td>Arquivo</td>
        </tr>
        <c:forEach items="${produtos}" var="produto">
	        <tr>
	            <td><a href="<c:url value="/produtos/detalhe/${produto.id }"/>" >${produto.titulo}</a></td>
	            <td>${produto.descricao}</td>
	            <td>${produto.paginas}</td>
	            <td><a href="${produto.sumarioPath}" >${produto.sumarioPath}</a></td>
	        </tr>
        </c:forEach>
    </table>

</body>
</html>