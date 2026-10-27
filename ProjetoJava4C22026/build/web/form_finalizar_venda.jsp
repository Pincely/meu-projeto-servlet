<%-- 
    Document   : index
    Created on : 04/08/2026, 15:15:21
    Author     : adm_pfelacio
--%>

<%@page import="model.VendaProduto"%>
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
        <script type="text/javascript">
            function retirarProduto(index, item){
                
                if(confirm("Tem certeza que deseja retirar o item "+item+"?")){
                    window.open("gerenciar_venda.do?acao=retirarProduto&index="+index,"_self");
                }
                
            }
        </script>
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
                    
                    <table class="table table-hover table-striped table-bordered 
                           display" id="listarProduto"> 
                        <thead>
                            <tr>
                                <th>ITEM</th>
                                <th>PRODUTO</th>
                                <th>QTD</th>
                                <th>VALOR</th>
                                <th>TOTAL</th>
                                <th>REMOVER</th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <th>ITEM</th>
                                <th>PRODUTO</th>
                                <th>QTD</th>
                                <th>VALOR</th>
                                <th>TOTAL</th>
                                <th>REMOVER</th>
                            </tr>
                        </tfoot>
                        <tbody>
                            <%
                                double total=0;
                                int cont=0;
                                for(VendaProduto vp:v.getCarrinho()){

                            %>
                            <tr>
                                <td align="center"><%=cont+1%></td>
                                <td><%=vp.getProduto().getNome()%></td>
                                <td><%=vp.getQtdVendida()%></td>
                                <td>R$ <%=vp.getValorVendido()%></td>
                                <td>R$ <%=vp.getQtdVendida()*vp.getValorVendido()%></td>
                                <td align="center">
                                    <a href="#" onclick="retirarProduto(<%=cont%>,<%=cont+1%>)"
                                       class="btn btn-danger">
                                        <i class="glyphicon glyphicon-trash"></i>
                                    </a>
                                </td>
                            </tr>
                            <%
                                total = total + (vp.getQtdVendida()*vp.getValorVendido());
                                cont++;
                            }
                            %>
                        </tbody>
                    </table>    
                        <div>Valor Total ==============> R$ <%=total%></div>
                      
                    <div class="row">
                        <a href="gerenciar_cliente.do?acao=listarTodos" class="btn btn-warning">
                            <i class="glyphicon glyphicon">Cancelar</i>
                        </a>
                        
                        <a href="form_venda.jsp" class="btn btn-warning">
                           <i class="glyphicon glyphicon">Continuar Vendendo</i> 
                        </a>    
                        <a href="gerenciar_venda.do?acao=gravar" class="btn btn-success">
                           <i class="glyphicon glyphicon">Confirmar Venda</i> 
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

