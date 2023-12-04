package Itens;

import Sistema.Restaurante;

abstract public class Item{
    protected String nome;
    protected String codigo;
    protected double precoUnitario;
    protected double precoCusto = 0;

    public Item(String nome, String codigo, double precoUnitario, double precoCusto){
        this.nome = nome;
        this.codigo = codigo;
        this.precoUnitario = precoUnitario;
        this.precoCusto = precoCusto;
    }

    public Item(String nome, String codigo, double precoUnitario){
        this.nome = nome;
        this.codigo = codigo;
        this.precoUnitario = precoUnitario;
    }

    public void venda(){
        Restaurante.adicionarCaixa(precoUnitario - precoCusto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }
}
