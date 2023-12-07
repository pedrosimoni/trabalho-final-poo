package Itens;

import Excecoes.IngredientesInsuficientes;
import Sistema.Restaurante;
import java.io.Serializable;

abstract public class Item implements PodeVender, Serializable {
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

    public void venda() throws IngredientesInsuficientes{
        Restaurante.adicionarCaixa(precoUnitario - precoCusto);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public double getPrecoUnitario() {
        return precoUnitario;
    }

}
