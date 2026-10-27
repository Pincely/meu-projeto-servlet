<%-- 
    Document   : index
    Created on : 04/08/2026, 15:15:21
    Author     : adm_pfelacio
--%>

<%@page import="model.Cliente"%>
<%@page import="model.Venda"%>
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
        <title>Nova Venda</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>    
            <%@include file="menu.jsp" %>
         
            <div class="content">
                <div class="form-container">
                    <%
                        Venda v = new Venda();
                        Cliente c = new Cliente();
                        try{
                            v = (Venda) session.getAttribute("venda");
                        }catch(Exception e){
                            out.print("Erro:"+e.getMessage());
                        }
                    %>
                    <br><br>Vendedor: <%=v.getVendedor().getNome()%><br>
                    Cliente: <%=v.getCliente().getNomeRazao()%><br>
                    <h4>Catalogo: (<%=v.getCarrinho().size()%> itens no carrinho)
                    
                    
                    <jsp:useBean class="model.ProdutoDAO" id="pDAO"/>
                    <c:forEach var="p" items="${pDAO.lista}">
                        <div id="prod${p.idProduto}">
                            <form method="get" action="gerenciar_venda.do">
                                <input type="hidden" name="idProduto" id="idProduto" value="${p.idProduto}"/>
                                <input type="hidden" name="acao" value="adicionarProduto"/>
                                ${p.nome}
                                <input type="number" name="qtd" id="qtd" style="width: 40px;"/>
                                <button type="submit" class="btn btn-default">
                                    <img src="imagens/carrinho.png" width="20px" height="20px">
                                </button>
                            </form>
                        </div>    
                    </c:forEach>    
                    <div class="row">
                        <a href="gerenciar_cliente.do?acao=listarTodos" class="btn btn-warning">
                            Cancelar
                        </a>
                        <a href="form_finalizar_venda.jsp" class="btn btn-success">
                            Finalizar Venda
                        </a>    
                    </div>
                </div>
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

