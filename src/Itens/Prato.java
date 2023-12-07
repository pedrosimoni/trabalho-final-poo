package Itens;

import Excecoes.IngredientesInsuficientes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println(nome + ": ");
        System.out.println("    " + descricao);
        System.out.println("    R$" + precoUnitario);
    }

    public void venda() throws IngredientesInsuficientes{
        super.venda();
        for(Ingrediente i: ingredientes){
            try{
                i.venda();
            }catch(IngredientesInsuficientes p){
                p.toString();
                Scanner sc = new Scanner(System.in);
                if(sc.nextLine().equals("s")){
                    System.out.print("\nDigite a quantidade: ");
                    i.compra(sc.nextInt());
                    try{
                        i.venda();
                    }catch(IngredientesInsuficientes pn){
                        throw p;
                    }
                }else{
                    throw p;
                }
            }
        }
    }

    public void calculaPrecoCusto(){
        precoCusto = 0;
        for(Ingrediente i : ingredientes){
            precoCusto += i.getPrecoCusto();
        }
    }
}
