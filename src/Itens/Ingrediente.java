package Itens;

import Excecoes.IngredientesInsuficientes;
import java.io.*;

public class Ingrediente implements Serializable{
    private String nome;
    private int qntd;
    private double precoCusto;

    public Ingrediente(String nome, int qntd, double precoCusto){
        this.nome = nome;
        this.qntd = qntd;
        this.precoCusto = precoCusto;
    }

    public void venda() throws IngredientesInsuficientes{
        if(qntd == 0){
            throw new IngredientesInsuficientes("Ingrediente " + nome + "insuficiente! Deseja comprar mais?(s/n)");
        }else{
            qntd--;
        }
    }

    public void compra(int qntdComprada){
        qntd += qntdComprada;
    }

    public String getNome(){
        return nome;
    }

    public double getPrecoCusto(){
        return precoCusto;
    }

    public void mostrar(){
        System.out.println("Nome: " + nome);
        System.out.println("    Preço custo: " + precoCusto);
    }
}
