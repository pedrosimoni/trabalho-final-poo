package Itens;

import java.io.Serializable;
import java.util.ArrayList;

public class Prato extends Item{
    protected ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
    protected String descricao;
    protected String tempoPreparo;

    public Prato (String nome, String codigo, double precoUnitario, ArrayList<Ingrediente> ingredientes, String descricao, String tempoPreparo){
        super(nome, codigo, precoUnitario);
        this.ingredientes.addAll(ingredientes);
        this.descricao = descricao;
        this.tempoPreparo = tempoPreparo;
    }

    public void mostrarBasico(){
        System.out.print(" : " + nome);
        System.out.print("    " + descricao);
        System.out.print("    R$" + precoUnitario);
    }

    public void venda(){
        super.venda();
        for(Ingrediente i: ingredientes){
            i.venda();
        }
    }

    public void calculaPrecoCusto(){
        for(Ingrediente i : ingredientes){
            precoCusto += i.getPrecoCusto();
        }
    }
}
