
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Menu;
import model.Usuario;
import model.UsuarioDAO;


public class GerenciarLogin extends HttpServlet {

    private static HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        GerenciarLogin.response=response;
        request.getSession().removeAttribute("ulogado");
        response.sendRedirect("form_login.jsp");
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        GerenciarLogin.response = response;
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        ArrayList<String> erros = new ArrayList<>();
        if(login==null || login.trim().isEmpty())
            erros.add("Preencha o login");
        if(senha==null || senha.trim().isEmpty())
            erros.add("Preencha a senha");
        
        if(erros.size()>0){
            String campos="";
            for(String erro: erros){
                campos+="\\n - "+erro;
            }
            exibirMensagem("Preecnha o(s) campo(s)"+campos);
        }else{
            Usuario u =new Usuario();
            try{
                UsuarioDAO uDAO = new UsuarioDAO();
                u = uDAO.getRecuperarUsuario(login);
                if((u.getIdUsuario()>0)&&(u.getSenha().equals(senha))){
                    HttpSession sessao = request.getSession();
                    sessao.setAttribute("ulogado", u);
                    response.sendRedirect("index.jsp");
                }else{
                    exibirMensagem("Login ou senha inválidos");
                }
            
            }catch(Exception e){
                exibirMensagem("Erro ao acessar o banco");
            }
        }
    }
    
    private static void exibirMensagem(String mensagem){
            try{
                PrintWriter out = response.getWriter();
                out.println("<script type='text/javascript'>");
                out.println("alert('"+mensagem+"')");
                out.println("history.back();");
                out.println("</script>");
                out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    public static Usuario verificarAcesso(HttpServletRequest request,
            HttpServletResponse response){
    
        Usuario u = null;
        GerenciarLogin.response= response;
        try{
            HttpSession sessao = request.getSession();
            if(sessao.getAttribute("ulogado")==null){
                response.sendRedirect("form_login.jsp");
            }else{
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();
                if(queryString!=null){
                    uri += "?" +queryString;
                }
                u = (Usuario) request.getSession().getAttribute("ulogado");
                if(u==null){
                    sessao.setAttribute("mensagem", "Você não está autenticado");
                    response.sendRedirect("form_login.jsp");
                }else{
                    boolean possuiAcesso = false;
                    for(Menu m: u.getPerfil().getMenus().getMenusVinculados()){
                        if(uri.contains(m.getLink())){
                            possuiAcesso = true;
                            break;
                        }
                    }
                    if(!possuiAcesso){
                        exibirMensagem("Acesso Negado!");
                    }
                }
            }
        }catch(Exception e){
            exibirMensagem("Exceção: "+e);
        }
        return u;
    
    }
    
     public static boolean verificarPermissao(HttpServletRequest request,
            HttpServletResponse response){
    
        Usuario u = null;
        GerenciarLogin.response= response;
        boolean possuiAcesso = false;
        try{
            HttpSession sessao = request.getSession();
            if(sessao.getAttribute("ulogado")==null){
                response.sendRedirect("form_login.jsp");
            }else{
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();
                if(queryString!=null){
                    uri += "?" +queryString;
                }
                u = (Usuario) request.getSession().getAttribute("ulogado");
                if(u==null){
                    sessao.setAttribute("mensagem", "Você não está autenticado");
                    response.sendRedirect("form_login.jsp");
                }else{
                    
                    for(Menu m: u.getPerfil().getMenus().getMenusVinculados()){
                        if(uri.contains(m.getLink())){
                            possuiAcesso = true;
                            break;
                        }
                    }
                    if(!possuiAcesso){
                        exibirMensagem("Acesso Negado!");
                    }
                }
            }
        }catch(Exception e){
            exibirMensagem("Exceção: "+e);
        }
        return possuiAcesso;
    
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
