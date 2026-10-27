/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author adm_pfelacio
 */
public class VendaProduto {
    
    private long id;
    private int qtdVendida;
    private double valorVendido;
    private Venda venda;
    private Produto produto;

    public VendaProduto() {
    }

    public VendaProduto(long id, int qtdVendida, double valorVendido, Venda venda, Produto produto) {
        this.id = id;
        this.qtdVendida = qtdVendida;
        this.valorVendido = valorVendido;
        this.venda = venda;
        this.produto = produto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQtdVendida() {
        return qtdVendida;
    }

    public void setQtdVendida(int qtdVendida) {
        this.qtdVendida = qtdVendida;
    }

    public double getValorVendido() {
        return valorVendido;
    }

    public void setValorVendido(double valorVendido) {
        this.valorVendido = valorVendido;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "VendaProduto{" + "id=" + id + ", qtdVendida=" + qtdVendida + ", valorVendido=" + valorVendido + ", venda=" + venda + ", produto=" + produto + '}';
    }
    
    
    
}
