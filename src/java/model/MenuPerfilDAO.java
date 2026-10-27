
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MenuPerfilDAO extends DataBaseDAO{

    public MenuPerfilDAO() throws ClassNotFoundException {
    }
    
    public boolean vincular(int idMenu, int idPerfil){
        boolean sucesso = false;
        String sql = "INSERT INTO menu_perfil (menu_idMenu, perfil_idPerfil) VALUES(?,?)";
        try{
            this.conectar();
            try(PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1,idMenu);
                pstm.setInt(2,idPerfil);
                int rowsAffected = pstm.executeUpdate();
                if(rowsAffected>0){
                    sucesso = true;
                }
            }
        }catch(SQLException e){
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return sucesso;
    }
    public boolean desvincular(int idMenu, int idPerfil){
    
        boolean sucesso = false;
        String sql = "DELETE FROM menu_perfil WHERE menu_idMenu=? AND perfil_idPerfil=?";
        try{
            this.conectar();
            try(PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1,idMenu);
                pstm.setInt(2,idPerfil);
                int rowsAffected = pstm.executeUpdate();
                if(rowsAffected>0){
                    sucesso = true;
                }
            }
        }catch(SQLException e){
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return sucesso;
    
    }
    
    
    public ArrayList<Menu> menusVinculadosPorPerfil(int idPerfil){
        ArrayList<Menu> lista = new ArrayList<>();
        String sql = "SELECT m.* FROM menu_perfil as mp, menu as m "
                + " WHERE mp.menu_idMenu = m.idMenu "
                + " AND mp.perfil_idPerfil=? ";
        try{
            this.conectar();
            try(PreparedStatement pstm  = conn.prepareStatement(sql)){
                pstm.setInt(1,idPerfil);
                try(ResultSet rs = pstm.executeQuery()){
                    while(rs.next()){
                         Menu m = new Menu();
                         m.setIdMenu(rs.getInt("m.idMenu"));
                         m.setNome(rs.getString("m.nome"));
                         m.setLink(rs.getString("m.link"));
                         m.setIcone(rs.getString("m.icone"));
                         m.setExibir(rs.getInt("m.exibir"));
                         lista.add(m);
                    }
                }
            }
        }catch(SQLException e){
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return lista;
    
    }
    public ArrayList<Menu> menusNaoVinculadosPorPerfil(int idPerfil){
        
        ArrayList<Menu> lista = new ArrayList<>();
        String sql = "SELECT m.* FROM menu as m "
                + " WHERE m.idMenu "
                + " NOT IN (SELECT menu_idMenu FROM menu_perfil "
                + "         WHERE perfil_idPerfil=? ) ";
        
        try{
            this.conectar();
            try(PreparedStatement pstm  = conn.prepareStatement(sql)){
                pstm.setInt(1,idPerfil);
                try(ResultSet rs = pstm.executeQuery()){
                    while(rs.next()){
                         Menu m = new Menu();
                         m.setIdMenu(rs.getInt("m.idMenu"));
                         m.setNome(rs.getString("m.nome"));
                         m.setLink(rs.getString("m.link"));
                         m.setIcone(rs.getString("m.icone"));
                         m.setExibir(rs.getInt("m.exibir"));
                         lista.add(m);
                    }
                }
            }
        }catch(SQLException e){
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return lista;
    
    }
    
    public MenuPerfil getCarregaMenusPorPerfil (int idPerfil){
        MenuPerfil mp = new MenuPerfil();
        mp.setMenusVinculados(menusVinculadosPorPerfil(idPerfil));
        mp.setMenusNaoVinculados(menusNaoVinculadosPorPerfil(idPerfil));
        return mp;
    }
}
