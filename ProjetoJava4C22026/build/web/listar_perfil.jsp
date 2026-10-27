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
            function confirmarExclusao(idPerfil, nome){
                
                if(confirm('Deseja realmente desativar o perfil '+nome+ '?')){
                    window.open("gerenciar_perfil.do?acao=desativar&idPerfil="+idPerfil,"_self");
                }
            }
        </script>
        <title>Home</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>    
            <%@include file="menu.jsp" %>
         
            <div class="content">
                <h2>Lista de Perfis</h2>
                <a href="form_perfil.jsp" class="btn btn-primary">Novo Cadastro</a>
                <table class="table table-hover table-striped 
                       table-bordered display" id="listarPerfil">
                    <thead>
                        <tr>
                            <th>idPerfil</th>
                            <th>Nome</th>
                            <th>Status</th>
                            <th>Opções</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>idPerfil</th>
                            <th>Nome</th>
                            <th>Status</th>
                            <th>Opções</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach var="p" items="${listarTodos}">
                            <tr>
                                <td>${p.idPerfil}</td>
                                <td>${p.nome}</td>
                                <td>
                                    <c:if test="${p.status==1}">Ativo</c:if>
                                    <c:if test="${p.status==2}">Inativo</c:if>
                                </td>
                                <td>
                                    <a href="gerenciar_perfil.do?acao=alterar&idPerfil=${p.idPerfil}"
                                       class="btn btn-primary">
                                        <i class="glyphicon glyphicon-pencil"></i>
                                    </a>
                                       <button class="btn btn-danger" 
                                               onclick="confirmarExclusao(${p.idPerfil},'${p.nome}')">  
                                           <i class="glyphicon glyphicon-trash"></i>
                                       </button>   
                                           
                                               <a class="btn btn-default" 
                                                  href="gerenciar_menu_perfil.do?acao=gerenciar&idPerfil=${p.idPerfil}">
                                                    <i class="glyphicon">Acessos</i>
                                               </a>    
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
                   $("#listarPerfil").DataTable({
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
