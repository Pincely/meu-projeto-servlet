


//Completar o ProdutoDAO



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
import model.Menu;
import model.MenuDAO;
import model.Produto;
import model.ProdutoDAO;


public class GerenciarProduto extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
       PrintWriter out = response.getWriter();
       String acao = request.getParameter("acao");
       String idProdutoStr = request.getParameter("idProduto");
       Produto p = new Produto();
       try{
           ProdutoDAO pDAO = new ProdutoDAO();
           int idProduto=0;
           if(acao.equals("listarTodos")){
               if(GerenciarLogin.verificarPermissao(request, response)){
                    ArrayList<Produto> listarTodos = new ArrayList<>();
                    listarTodos = pDAO.getLista();
                    if(listarTodos.size()>0){
                        RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_produto.jsp");
                        request.setAttribute("listarTodos", listarTodos);
                        disp.forward(request, response);
                    }else{
                        exibirMensagem("Lista vazia", true, "form_produto.jsp", response);
                    }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }
           }
     
    if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){   
                   try{
                       idProduto = Integer.parseInt(idProdutoStr);
                       p = pDAO.getCarregaPorID(idProduto);
                       if(p.getIdProduto()>0){
                           RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_produto.jsp");
                           request.setAttribute("produto", p);
                           disp.forward(request, response);
                       }else{
                           exibirMensagem("Produto não encontrado",false,"",response);
                       }
                   }catch(NumberFormatException e){
                       exibirMensagem("ID do produto inválido",false,"",response);
                   }
                }else{
                   exibirMensagem("Acesso negado", false,"",response);
               }  
           }
           if(acao.equals("desativar")){
              if(GerenciarLogin.verificarPermissao(request, response)){ 
                    try{
                        idProduto = Integer.parseInt(idProdutoStr);
                        if(idProduto!=0){
                            p.setIdProduto(idProduto);
                            if(pDAO.desativar(p)){
                                exibirMensagem("Produto desativado com sucesso",true,
                                        "gerenciar_produto.do?acao=listarTodos",response);
                            }else{
                                exibirMensagem("Erro ao desativar",false,"",response);
                            }
                        }
                    }catch(NumberFormatException e){
                        exibirMensagem("ID de produto inválido",false,"",response);
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
       
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        ArrayList<String> erros = new ArrayList<String>();
        String idProdutoStr = request.getParameter("idProduto");
        String nome = request.getParameter("nome");
        String valorStr = request.getParameter("valor");
        String qtdStr = request.getParameter("qtd");
        String statusStr = request.getParameter("status");
        
        try{
            ProdutoDAO pDAO = new ProdutoDAO();
            int status =0;
            int idProduto=0;
            int qtd=0;
            double valor=0;
            if(idProdutoStr!=null && !idProdutoStr.isEmpty()){
                try{
                    idProduto = Integer.parseInt(idProdutoStr);
                }catch(NumberFormatException n){
                    erros.add("ID de Produto inválido");
                }
            }
            if(statusStr!=null && !statusStr.isEmpty()){
                try{
                    status = Integer.parseInt(statusStr);
                }catch(NumberFormatException n){
                    erros.add("Status do produto inválido");
                }
            }
            if(qtdStr!=null && !qtdStr.isEmpty()){
                try{
                    qtd = Integer.parseInt(qtdStr);
                }catch(NumberFormatException n){
                    erros.add("Quantidade do produto inválido");
                }
            }
            if(valorStr!=null && !valorStr.isEmpty()){
                try{
                    valor = Double.parseDouble(valorStr.replace(".","").replace(",","."));
                }catch(NumberFormatException n){
                    erros.add("Valor do produto inválido");
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
                Produto p = new Produto();
                p.setIdProduto(idProduto);
                p.setNome(nome);
                p.setQtd(qtd);
                p.setValor(valor);
                p.setStatus(status);
                boolean sucesso = pDAO.gravar(p);
                if(sucesso){
                    exibirMensagem("Produto gravado com sucesso", true,
                            "gerenciar_produto.do?acao=listarTodos",response);
                }else{
                    exibirMensagem("Erro ao gravar o Produto",false,"",response);
                }
            }
        }catch(ClassNotFoundException ex){
            Logger.getLogger(GerenciarProduto.class.getName()).log(Level.SEVERE,null,ex);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
