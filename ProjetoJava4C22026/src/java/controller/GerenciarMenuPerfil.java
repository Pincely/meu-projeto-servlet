/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MenuPerfil;
import model.MenuPerfilDAO;
import model.Perfil;
import model.PerfilDAO;


public class GerenciarMenuPerfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String idPerfilStr = request.getParameter("idPerfil");
        String acao = request.getParameter("acao");
        Perfil p = new Perfil();
        try{
            PerfilDAO pDAO = new PerfilDAO();
            MenuPerfilDAO mpDAO = new MenuPerfilDAO();
            MenuPerfil mp = new MenuPerfil();
            int idPerfil=0;
            if(acao.equals("gerenciar")){
               if(GerenciarLogin.verificarPermissao(request, response)){ 
                    try{
                        idPerfil = Integer.parseInt(idPerfilStr);
                        p = pDAO.getCarregaPorID(idPerfil);
                        if(p.getIdPerfil()>0){
                            RequestDispatcher disp = 
                                    getServletContext().getRequestDispatcher("/form_menu_perfil.jsp");
                            request.setAttribute("perfil", p);
                            disp.forward(request, response);
                        }else{
                            exibirMensagem("Perfil não encontrado",false,"",response);
                        }
                    }catch(NumberFormatException ex){
                        exibirMensagem("idPerfil invalido",false,"",response);
                    }
               }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }    
            }
            if(acao.equals("desvincular")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                        String idMenuStr = request.getParameter("idMenu");
                        int idMenu=0;
                        try{
                            idPerfil = Integer.parseInt(idPerfilStr);
                            if(idMenuStr.equals("")||idMenuStr.isEmpty()){
                                exibirMensagem("O menu deve ser selecionado",false,"",response);
                            }else{
                                idMenu = Integer.parseInt(idMenuStr);
                            }
                            if(mpDAO.desvincular(idMenu, idPerfil)){
                                exibirMensagem("Desvinculado com sucesso",true,"gerenciar_menu_perfil.do?acao=gerenciar&idPerfil="+idPerfilStr,response);
                            }else{
                                exibirMensagem("Erro ao desvincular",false,"",response);
                            }

                        }catch(NumberFormatException e){
                            exibirMensagem("idPerfil ou idMenu invalido invalido",false,"",response);
                        }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }
            
            }
        
        }catch(Exception e){
            out.print(e);
            exibirMensagem("Erro ao acessar o banco",false,"",response);
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String idMenuStr = request.getParameter("idMenu");
        String idPerfilStr = request.getParameter("idPerfil");
        try{
            MenuPerfilDAO mpDAO = new MenuPerfilDAO();
            if(idMenuStr.equals("")||idMenuStr.isEmpty()||idPerfilStr.equals("")||idPerfilStr.isEmpty()){
                exibirMensagem("Campos obrigatorios devem ser selecionados",false,"",response);
            }else{
                if(mpDAO.vincular(Integer.parseInt(idMenuStr), Integer.parseInt(idPerfilStr))){
                    exibirMensagem("Vinculado com sucesso",true,"gerenciar_menu_perfil.do?acao=gerenciar&idPerfil="+idPerfilStr,response);
                }else{
                    exibirMensagem("Erro ao vincular",false,"",response);
                }
            }

        }catch(Exception e){
            out.print(e);
            exibirMensagem("Erro ao acessar o banco",false,"",response);
        }
        
    }
    
     private static void 
        exibirMensagem(String mensagem, boolean resposta, String link, HttpServletResponse response){
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
