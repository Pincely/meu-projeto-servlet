/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author adm_pfelacio
 */
public class VendaDAO extends DataBaseDAO{

    public VendaDAO() throws ClassNotFoundException {
    }
    
    public boolean gravar(Venda v){
    
        boolean sucesso = false;
        boolean sucesso_item  = false;
        boolean retorno = false;
        String sql;
        int cont =0;
        sql = "INSERT INTO venda(status, dataVenda, usuario_idUsuario, cliente_idCliente) VALUES(?,now(),?,?)";
        try{
            this.conectar();
            try(PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setInt(1, 1);//status já entra 1 venda ativa
                pstm.setInt(2,v.getVendedor().getIdUsuario());
                pstm.setInt(3, v.getCliente().getIdCliente());
                int rowsAffected = pstm.executeUpdate();
                if(rowsAffected>0){
                    sucesso=true;
                    ResultSet rs = pstm.getGeneratedKeys();
                    if(rs.next()){
                        v.setIdVenda(rs.getInt(1));
                    }
                    for(VendaProduto vp: v.getCarrinho()){
                        String sql_item="INSERT INTO venda_produto (venda_idVenda, produto_idProduto, qtdVendida, valorVendido) VALUES (?,?,?,?)";
                        try(PreparedStatement pstm_item = conn.prepareStatement(sql_item)){
                            pstm_item.setInt(1,v.getIdVenda());
                            pstm_item.setInt(2,vp.getProduto().getIdProduto());
                            pstm_item.setInt(3, vp.getQtdVendida());
                            pstm_item.setDouble(4,vp.getValorVendido());
                            int rowsAffected_item = pstm_item.executeUpdate();
                            if(rowsAffected_item>0){
                                cont++;
                            }
                        
                        }
                    }
                    if(cont==v.getCarrinho().size()){
                        sucesso_item=true;
                    }
                }
            }
            if(sucesso && sucesso_item){
                retorno = true;
            }
        }catch(SQLException e){
                Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally{
            this.desconectar();
        }
        return retorno;
    }
}
