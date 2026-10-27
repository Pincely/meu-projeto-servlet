/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adm_pfelacio
 */
public class ClienteDAO extends DataBaseDAO{

    public ClienteDAO() throws ClassNotFoundException {
    }
    public ArrayList<Cliente> getLista(){
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try{
            //abre a conexao
            this.conectar();
            //usando try-with-resource para PreparedStatement e ResultSet
            try(PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()){
                while(rs.next()){
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setNomeRazao(rs.getString("nomeRazao"));
                    c.setCpfCnpj(rs.getString("cpfCnpj"));
                    c.setRgIe(rs.getString("rgIe"));
                    c.setDataNascAbertura(rs.getDate("dataNascAbertura"));
                    c.setTipo(rs.getInt("tipo"));
                    c.setStatus(rs.getInt("status"));
                    lista.add(c);
                }  
            }    
        }catch(SQLException e){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return lista;
    }
    
     public Cliente getCarregaPorID(int idCliente){
        Cliente c = new Cliente();
        String sql = "SELECT * FROM cliente WHERE idCliente=?";
        try{
            //abre a conexao
            this.conectar();
            //usando try-with-resource para PreparedStatement e ResultSet
            try(PreparedStatement pstm = conn.prepareStatement(sql)){
                pstm.setInt(1, idCliente);    
                try(ResultSet rs = pstm.executeQuery()){
                    if(rs.next()){
                        c = new Cliente();
                        c.setIdCliente(rs.getInt("idCliente"));
                    c.setNomeRazao(rs.getString("nomeRazao"));
                    c.setCpfCnpj(rs.getString("cpfCnpj"));
                    c.setRgIe(rs.getString("rgIe"));
                    c.setDataNascAbertura(rs.getDate("dataNascAbertura"));
                    c.setTipo(rs.getInt("tipo"));
                    c.setStatus(rs.getInt("status"));
                    }  
                }            
            }    
        }catch(SQLException e){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return c;
    }
}
