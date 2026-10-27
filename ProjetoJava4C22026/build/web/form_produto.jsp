<%-- 
    Document   : index
    Created on : 04/08/2026, 15:15:21
    Author     : adm_pfelacio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/estilo.css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css"/>
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css"/>
        <title>Cadastrar Produto</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>    
            <%@include file="menu.jsp" %>
         
            <div class="content">
                <div class="form-container">
                    <form method="POST" action="gerenciar_produto.do">
                        <legend>Formulário de Produto</legend>
                        <input type="hidden" name="idProduto" id="idProduto" value="${produto.idProduto}"/>
                        <label for="nome">Nome</label>
                        <input type="text" name="nome" id="nome" value="${produto.nome}"/>
                        <label for="qtd">QTD</label>
                        <input type="text" name="qtd" id="qtd" value="${produto.qtd}"/>
                        <label for="valor">Valor</label>
                        <input type="text" name="valor" id="valor" value="<fmt:formatNumber pattern="#,##0.00" value="${produto.valor}"/>"/>
                        <label for="status">Status</label>
                        <select name="status" required="">
                            <c:if test="${produto.status==null}">
                                <option value="0">Escolha uma opção</option>
                                <option value="1">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:if> 
                            <c:if test="${produto.status==1}">
                                <option value="1" selected="">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:if> 
                            <c:if test="${produto.status==2}">
                                <option value="1">Ativo</option>
                                <option value="2" selected="">Inativo</option>
                            </c:if> 
                        </select>
                        <div class="form button form">
                            <button type="submit" class="btn btn-success">
                                Gravar
                            </button>
                            <a href="gerenciar_produto.do?acao=listarTodos"
                               class="btn btn-warning">Voltar</a>
                        </div>
                    </form>
                </div>
            </div>    
            <script type="text/javascript" src="datatables/jquery.js"></script>
            <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
            <script type="text/javascript">
                $(document).ready(function(){
                   $("#listarProduto").DataTable({
                     "language":{
                         "url":"datatables/portugues.json"
                     }  
                   });
                });
                
                function toggleMenu(){
                    var menu = document.getElementById("nav-links");
                    menu.classList.toggle("show");
                }
            </script>    
       
    </body>
</html>
