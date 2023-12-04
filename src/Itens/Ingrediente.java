package Itens;
import Excecoes.IngredientesInsuficientes;

import java.io.*;

public class Ingrediente implements PodeVender, Serializable{
    private String nome;
    private int qntd;
    private double precoCusto;

    public Ingrediente(String nome, int qntd, double precoCusto){
        this.nome = nome;
        this.qntd = qntd;
        this.precoCusto = precoCusto;
    }

    public void venda(){
        if(qntd == 0){
            //throw new IngredientesInsuficientes("Ingrediente " + nome + "insuficiente! Deseja comprar mais?(y/n)");
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
}
