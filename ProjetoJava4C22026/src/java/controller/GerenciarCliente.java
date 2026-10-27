
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
            if(acao.equals("alterar")){//MUDAR
               if(GerenciarLogin.verificarPermissao(request, response)){
                    try{
                        idMenu = Integer.parseInt(idMenuStr);
                        m = mDAO.getCarregaPorID(idMenu);
                        if(m.getIdMenu()>0){
                            RequestDispatcher disp =getServletContext().getRequestDispatcher("/form_menu.jsp");
                            request.setAttribute("menu", m);
                            disp.forward(request, response);
                        }else{
                            exibirMensagem("Menu não encontrado",false,"",response);
                        }
                    }catch(NumberFormatException e){
                        exibirMensagem("ID de Menu inválido",false,"",response);
                    }
               }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }
           }
           if(acao.equals("excluir")){//MUDAR
               if(GerenciarLogin.verificarPermissao(request, response)){
                    try{
                        idMenu = Integer.parseInt(idMenuStr);
                        if(idMenu!=0){
                            m.setIdMenu(idMenu);
                            if(mDAO.excluir(m)){
                                exibirMensagem("Menu excluido com sucesso",true,
                                        "gerenciar_menu.do?acao=listarTodos",response);
                            }else{
                                exibirMensagem("Erro ao excluir",false,"",response);
                            }
                        }
                    }catch(NumberFormatException e){
                        exibirMensagem("ID de Menu inválido",false,"",response);
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        ArrayList<String> erros = new ArrayList<String>();
        //recebe os parametros
        String nome = request.getParameter("nome");
        String link = request.getParameter("link");
        String icone = request.getParameter("icone");
        String exibirStr = request.getParameter("exibir");
        String idMenuStr = request.getParameter("idMenu");
        try{
            ClienteDAO cDAO = new ClienteDAO();
            int exibir =0;
            int idCliente=0;
            if(idMenuStr!=null && !idMenuStr.isEmpty()){
                try{
                    idCliente = Integer.parseInt(idClienteStr);
                }catch(NumberFormatException n){
                    erros.add("ID de Menu inválido");
                }
            }
            if(exibirStr!=null && !exibirStr.isEmpty()){
                try{
                    exibir = Integer.parseInt(exibirStr);
                }catch(NumberFormatException n){
                    erros.add("exibir do cliente inválido");
                }
            }
            if(nome==null || nome.trim().isEmpty()){
                erros.add("O campo nome é obrigatório");
            }
            if(link==null || link.trim().isEmpty()){
                erros.add("O campo link é obrigatório");
            }
            if(erros.size()>0){
                String campos="";
                for(String erro:erros){
                    campos+="\\n - "+erro;
                }
                exibirMensagem("Preecha o(s) campo(s) "+campos,false,"",response);
            }else{
                Menu m = new Menu();
                m.setIdMenu(idMenu);
                m.setNome(nome);
                m.setLink(link);
                m.setIcone(icone);
                m.setExibir(exibir);
                boolean sucesso = mDAO.gravar(m);
                if(sucesso){
                    exibirMensagem("Menu gravado com sucesso", true,
                            "gerenciar_menu.do?acao=listarTodos",response);
                }else{
                    exibirMensagem("Erro ao gravar o Menu",false,"",response);
                }
            }
        }catch(ClassNotFoundException ex){
            Logger.getLogger(GerenciarMenu.class.getName()).log(Level.SEVERE,null,ex);
        }


                
        
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
