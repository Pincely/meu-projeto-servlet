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
        <script type="text/javascript">
            function confirmarExclusao(idMenu, nome){
                
                if(confirm('Deseja realmente excluir o Menu '+nome+ '?')){
                    window.open("gerenciar_menu.do?acao=excluir&idMenu="+idMenu,"_self");
                }
            }
        </script>
        <title>Listar Menu</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>    
            <%@include file="menu.jsp" %>
         
            <div class="content">
                <h2>Lista de Menus</h2>
                <a href="form_menu.jsp" class="btn btn-primary">Novo Cadastro</a>
                <table class="table table-hover table-striped 
                       table-bordered display" id="listarMenu">
                    <thead>
                        <tr>
                            <th>idMenu</th>
                            <th>Nome</th>
                            <th>Link</th>
                            <th>Icone</th>
                            <th>Exibir</th>
                            <th>Opções</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>idMenu</th>
                            <th>Nome</th>
                            <th>Link</th>
                            <th>Icone</th>
                            <th>Exibir</th>
                            <th>Opções</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach var="m" items="${listarTodos}">
                            <tr>
                                <td>${m.idMenu}</td>
                                <td>${m.nome}</td>
                                <td>${m.link}</td>
                                <td>${m.icone}</td>
                                <td>
                                    <c:if test="${m.exibir==1}">Sim</c:if>
                                    <c:if test="${m.exibir==2}">Não</c:if>
                                </td>
                                <td>
                                    <a href="gerenciar_menu.do?acao=alterar&idMenu=${m.idMenu}"
                                       class="btn btn-primary">
                                        <i class="glyphicon glyphicon-pencil"></i>
                                    </a>
                                       <button class="btn btn-danger" 
                                               onclick="confirmarExclusao(${m.idMenu},'${m.nome}')">  
                                           <i class="glyphicon glyphicon-trash"></i>
                                       </button>    
                                </td>
                            </tr>
                            
                            
                        </c:forEach>
                    </tbody>
                </table>    
            </div>    
            <script type="text/javascript" src="datatables/jquery.js"></script>
            <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
            <script type="text/javascript">
                $(document).ready(function(){
                   $("#listarMenu").DataTable({
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
