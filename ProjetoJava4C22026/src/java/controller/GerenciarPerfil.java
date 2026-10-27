/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Perfil;
import model.PerfilDAO;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author adm_pfelacio
 */
public class GerenciarPerfil extends HttpServlet {

   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       PrintWriter out = response.getWriter();
       String acao = request.getParameter("acao");
       String idPerfilStr = request.getParameter("idPerfil");
       Perfil p = new Perfil();
       try{
           PerfilDAO pDAO = new PerfilDAO();
           int idPerfil=0;
           if(acao.equals("listarTodos")){
               if(GerenciarLogin.verificarPermissao(request, response)){
                        ArrayList<Perfil> listarTodos = new ArrayList<>();
                        listarTodos = pDAO.getLista();
                        if(listarTodos.size()>0){
                            RequestDispatcher disp = 
                                    getServletContext().getRequestDispatcher("/listar_perfil.jsp");
                            request.setAttribute("listarTodos", listarTodos);
                            disp.forward(request, response);
                        }else{
                            exibirMensagem("Lista vazia", true, "form_perfil.jsp", response);
                        }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }
           }
           if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){   
                   try{
                       idPerfil = Integer.parseInt(idPerfilStr);
                       p = pDAO.getCarregaPorID(idPerfil);
                       if(p.getIdPerfil()>0){
                           RequestDispatcher disp =getServletContext().getRequestDispatcher("/form_perfil.jsp");
                           request.setAttribute("perfil", p);
                           disp.forward(request, response);
                       }else{
                           exibirMensagem("Perfil não encontrado",false,"",response);
                       }
                   }catch(NumberFormatException e){
                       exibirMensagem("ID de perfil inválido",false,"",response);
                   }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }  
           }
           if(acao.equals("desativar")){
              if(GerenciarLogin.verificarPermissao(request, response)){ 
                    try{
                        idPerfil = Integer.parseInt(idPerfilStr);
                        if(idPerfil!=0){
                            p.setIdPerfil(idPerfil);
                            if(pDAO.desativar(p)){
                                exibirMensagem("Perfil desativado com sucesso",true,
                                        "gerenciar_perfil.do?acao=listarTodos",response);
                            }else{
                                exibirMensagem("Erro ao desativar",false,"",response);
                            }
                        }
                    }catch(NumberFormatException e){
                        exibirMensagem("ID de perfil inválido",false,"",response);
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
        String statusStr = request.getParameter("status");
        String idPerfilStr = request.getParameter("idPerfil");
        try{
            PerfilDAO pDAO = new PerfilDAO();
            int status =0;
            int idPerfil=0;
            if(idPerfilStr!=null && !idPerfilStr.isEmpty()){
                try{
                    idPerfil = Integer.parseInt(idPerfilStr);
                }catch(NumberFormatException n){
                    erros.add("ID de perfil inválido");
                }
            }
            if(statusStr!=null && !statusStr.isEmpty()){
                try{
                    status = Integer.parseInt(statusStr);
                }catch(NumberFormatException n){
                    erros.add("status do perfil inválido");
                }
            }
            if(nome==null || nome.trim().isEmpty()){
                erros.add("O campo nome é obrigatório");
            }
            if(erros.size()>0){
                String campos="";
                for(String erro:erros){
                    campos+="\\n - "+erro;
                }
                exibirMensagem("Preecha o(s) campo(s) "+campos,false,"",response);
            }else{
                Perfil p = new Perfil();
                p.setIdPerfil(idPerfil);
                p.setNome(nome);
                p.setStatus(status);
                boolean sucesso = pDAO.gravar(p);
                if(sucesso){
                    exibirMensagem("Perfil gravado com sucesso", true,
                            "gerenciar_perfil.do?acao=listarTodos",response);
                }else{
                    exibirMensagem("Erro ao gravar o perfil",false,"",response);
                }
            }
        }catch(ClassNotFoundException ex){
            Logger.getLogger(GerenciarPerfil.class.getName()).log(Level.SEVERE,null,ex);
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
