



package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.ClienteDAO;
import model.Perfil;
import model.PerfilDAO;


public class GerenciarCliente extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       PrintWriter out = response.getWriter();
       String acao = request.getParameter("acao");
       String idClienteStr = request.getParameter("idCliente");
       Cliente c = new Cliente();
       try{
           ClienteDAO cDAO = new ClienteDAO();
           int idCliente=0;
           if(acao.equals("listarTodos")){
               if(GerenciarLogin.verificarPermissao(request, response)){
                        ArrayList<Cliente> listarTodos = new ArrayList<>();
                        listarTodos = cDAO.getLista();
                        if(listarTodos.size()>0){
                            RequestDispatcher disp = 
                                    getServletContext().getRequestDispatcher("/listar_cliente.jsp");
                            request.setAttribute("listarTodos", listarTodos);
                            disp.forward(request, response);
                        }else{
                            exibirMensagem("Lista vazia", true, "form_cliente.jsp", response);
                        }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }
           }
           if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){   
                   try{
                       idCliente = Integer.parseInt(idClienteStr);
                       c = cDAO.getCarregaPorID(idCliente);
                       if(c.getIdCliente()>0){
                           RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_cliente.jsp");
                           request.setAttribute("cliente", c);
                           disp.forward(request, response);
                       }else{
                           exibirMensagem("Cliente não encontrado",false,"",response);
                       }
                   }catch(NumberFormatException e){
                       exibirMensagem("ID de cliente inválido",false,"",response);
                   }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }  
           }
           if(acao.equals("desativar")){
              if(GerenciarLogin.verificarPermissao(request, response)){ 
                    try{
                        idCliente = Integer.parseInt(idClienteStr);
                        if(idCliente!=0){
                            c.setIdCliente(idCliente);
                            if(cDAO.desativar(c)){
                                exibirMensagem("Cliente desativado com sucesso",true,
                                        "gerenciar_cliente.do?acao=listarTodos",response);
                            }else{
                                exibirMensagem("Erro ao desativar",false,"",response);
                            }
                        }
                    }catch(NumberFormatException e){
                        exibirMensagem("ID de cliente inválido",false,"",response);
                    }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }
           }

           }catch(Exception e){
           out.println(e);
           exibirMensagem("Erro ao acessar o banco",false,"",response);
       }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
     private static void 
        exibirMensagem(String mensagem, boolean resposta,
                String link, HttpServletResponse response){
            try{
                PrintWriter out = response.getWriter();
                out.println("<script type='text/javascript'>");
                out.println("alert('"+mensagem+"')");
                if(resposta){
                    out.println("location.href='"+link+"'");
                }else{
                    out.println("history.back();");
                }
                out.println("</script>");
                out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
