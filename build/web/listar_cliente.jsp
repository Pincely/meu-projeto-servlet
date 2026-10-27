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
        <script type="text/javascript">
            function confirmarExclusao(idCliente, nome){
                
                if(confirm('Deseja realmente desativar o Cliente '+nome+ '?')){
                    window.open("gerenciar_cliente.do?acao=desativar&idCliente="+idCliente,"_self");
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
                <h2>Lista de Clientes</h2>
                <a href="form_cliente.jsp" class="btn btn-primary">Novo Cadastro</a>
                <table class="table table-hover table-striped 
                       table-bordered display" id="listarCliente">
                    <thead>
                        <tr>
                            <th>idCliente</th>
                            <th>Nome /Razao Social</th>
                            <th>CPF/ CNPJ</th>
                            <th>RG / IE</th>
                            <th>Data de Nascimento/ Abertura</th>
                            <th>Tipo</th>
                            <th>Status</th>
                            <th>Opções</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <th>idCliente</th>
                            <th>Nome /Razao Social</th>
                            <th>CPF/ CNPJ</th>
                            <th>RG / IE</th>
                            <th>Data de Nascimento/ Abertura</th>
                            <th>Tipo</th>
                            <th>Status</th>
                            <th>Opções</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach var="c" items="${listarTodos}">
                            <tr>
                                <td>${c.idCliente}</td>
                                <td>${c.nomeRazao}</td>
                                <td>${c.cpfCnpj}</td>
                                <td>${c.rgIe}</td>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${c.dataNascAbertura}"/></td>
                                <td>
                                    <c:if test="${c.tipo==1}">Fisica</c:if>
                                    <c:if test="${c.tipo==2}">Juridica</c:if>
                                </td>
                                <td>
                                    <c:if test="${c.status==1}">Sim</c:if>
                                    <c:if test="${c.status==2}">Não</c:if>
                                </td>
                                <td>
                                    <a href="gerenciar_cliente.do?acao=alterar&idCliente=${c.idCliente}"
                                       class="btn btn-primary">
                                        <i class="glyphicon glyphicon-pencil"></i>
                                    </a>
                                       <button class="btn btn-danger" 
                                               onclick="confirmarExclusao(${c.idCliente},'${c.nomeRazao}')">  
                                           <i class="glyphicon glyphicon-trash"></i>
                                       </button>  
                                           
                                               <a class="btn btn-success" href="gerenciar_venda.do?acao=novaVenda&idCliente=${c.idCliente}">
                                         <i class="glyphicon glyphicon">Realizar Venda</i>
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
                   $("#listarCliente").DataTable({
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
