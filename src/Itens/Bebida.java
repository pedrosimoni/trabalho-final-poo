package Itens;
import Enums.EmbalagensEnum;

import java.io.Serializable;

public class Bebida extends Item implements PodeVender, Serializable {
    private int tamanho;
    private EmbalagensEnum tipoEmbalagem;

    public Bebida(String nome, String codigo, double precoUnitario, double precoCusto, int tamanho, String tipoEmbalagem){
        super(nome, codigo, precoUnitario, precoCusto);
        this.tamanho = tamanho;
        this.tipoEmbalagem = EmbalagensEnum.valueOf(tipoEmbalagem);
    }

    public void mostrarBasico(){
        System.out.print(" : " + nome);
        System.out.print("    " + tamanho);
        System.out.print("    " + tipoEmbalagem.toString());
        System.out.print("    R$" + precoUnitario);
    }

    public void venda(){
        super.venda();
    }
}
