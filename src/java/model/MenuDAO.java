
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MenuDAO extends DataBaseDAO{

    public MenuDAO() throws ClassNotFoundException {
    }
    
    public ArrayList<Menu> getLista(){
        ArrayList<Menu> lista = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        try{
            //abre a conexao
            this.conectar();
            //usando try-with-resource para PreparedStatement e ResultSet
            try(PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()){
                while(rs.next()){
                    Menu m = new Menu();
                    m.setIdMenu(rs.getInt("idMenu"));
                    m.setNome(rs.getString("nome"));
                    m.setLink(rs.getString("link"));
                    m.setIcone(rs.getString("icone"));
                    m.setExibir(rs.getInt("exibir"));
                    lista.add(m);
                }  
            }    
        }catch(SQLException e){
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return lista;
    }
    
    public Menu getCarregaPorID(int idMenu){
        Menu m = new Menu();
        String sql = "SELECT * FROM menu WHERE idMenu=?";
        try{
            //abre a conexao
            this.conectar();
            //usando try-with-resource para PreparedStatement e ResultSet
            try(PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, idMenu);    
                try(ResultSet rs = pstm.executeQuery()){
                    if(rs.next()){
                        m = new Menu();
                        m.setIdMenu(rs.getInt("idMenu"));
                        m.setNome(rs.getString("nome"));
                        m.setLink(rs.getString("link"));
                        m.setIcone(rs.getString("icone"));
                        m.setExibir(rs.getInt("exibir"));
                    }  
                }            
            }    
        }catch(SQLException e){
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return m;
    }
    
    public boolean gravar(Menu m){
    
        boolean sucesso = false;
        String sql;
        if(m.getIdMenu()==0){
            sql = "INSERT INTO menu(nome,link,icone,exibir) VALUES(?,?,?,?)";
        }else{
            sql = "UPDATE menu SET nome=?, link=?, icone=?, exibir=? WHERE idMenu=?";
        }
        try{
            this.conectar();
            try(PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setString(1, m.getNome());
                pstm.setString(2,m.getLink());
                pstm.setString(3,m.getIcone());
                pstm.setInt(4, m.getExibir());
                if(m.getIdMenu()>0){
                    pstm.setInt(5,m.getIdMenu());
                }
                int rowsAffected = pstm.executeUpdate();
                if(rowsAffected>0){
                    sucesso=true;
                }
            }
        }catch(SQLException e){
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return sucesso;
    }
    
    public boolean excluir(Menu m){
        
        boolean sucesso = false;
        try{
            this.conectar();
            String sql = "DELETE FROM menu WHERE idMenu=?";
            try(PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1,m.getIdMenu());
                int rowsAffected = pstm.executeUpdate();
                if(rowsAffected>0){
                    sucesso=true;
                }
            }
        }catch(SQLException e){
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return sucesso;
    }
    
}
