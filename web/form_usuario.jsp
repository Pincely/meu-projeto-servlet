<%-- 
    Document   : index
    Created on : 04/08/2026, 15:15:21
    Author     : adm_pfelacio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/estilo.css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css"/>
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css"/>
        <title>Cadastrar Usuário</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>    
            <%@include file="menu.jsp" %>
         
            <div class="content">
                <div class="form-container">
                    <form method="POST" action="gerenciar_usuario.do">
                        <legend>Formulário de Usuário</legend>
                        <input type="hidden" name="idUsuario" id="idUsuario" value="${usuario.idUsuario}"/>
                        <label for="nome">Nome</label>
                        <input type="text" name="nome" id="nome" value="${usuario.nome}"/>
                        <label for="login">Login</label>
                        <input type="text" name="login" id="login" value="${usuario.login}"/>
                        <label for="senha">Senha</label>
                        <input type="password" name="senha" id="senha" value="${usuario.senha}"/>
                        <label for="dataNasc">Data de Nascimento</label>
                        <input type="date" name="dataNasc" id="dataNasc" value="${usuario.dataNasc}"/>
                         <label for="Perfil">Perfil</label>
                         <select name="idPerfil" id="idPerfil" required="">
                             <option value="">Selecione o perfil</option>
                             <jsp:useBean class="model.PerfilDAO" id="pDAO"/>
                             <c:forEach var="p" items="${pDAO.lista}">
                                 <option value="${p.idPerfil}"
                                         <c:if test="${p.idPerfil==usuario.perfil.idPerfil}">
                                             selected=""
                                         </c:if>
                                  >
                                     ${p.nome}
                                 </option>
                             </c:forEach>
                         </select>
                         
                        <label for="status">Status</label>
                        <select name="status" required="">
                            <c:if test="${usuario.status==null}">
                                <option value="0">Escolha uma opção</option>
                                <option value="1">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:if> 
                            <c:if test="${usuario.status==1}">
                                <option value="1" selected="">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:if> 
                            <c:if test="${usuario.status==2}">
                                <option value="1">Ativo</option>
                                <option value="2" selected="">Inativo</option>
                            </c:if> 
                        </select>
                        <div class="form button form">
                            <button type="submit" class="btn btn-success">
                                Gravar
                            </button>
                            <a href="gerenciar_usuario.do?acao=listarTodos"
                               class="btn btn-warning">Voltar</a>
                        </div>
                    </form>
                </div>
            </div>    
            <script type="text/javascript" src="datatables/jquery.js"></script>
            <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
            <script type="text/javascript">
                $(document).ready(function(){
                   $("#listarUsuario").DataTable({
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
