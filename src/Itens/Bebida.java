package Itens;
import Enums.EmbalagensEnum;
import Sistema.Restaurante;

import java.io.Serializable;

public class Bebida extends Item implements PodeVender, Serializable {
    private int tamanho;
    private EmbalagensEnum tipoEmbalagem;
    private int quantidade;

    public Bebida(String nome, String codigo, double precoUnitario, double precoCusto, int tamanho, String tipoEmbalagem){
        super(nome, codigo, precoUnitario, precoCusto);
        this.tamanho = tamanho;
        this.tipoEmbalagem = EmbalagensEnum.valueOf(tipoEmbalagem);
    }

    public void mostrarBasico(){
        System.out.println(nome + ": ");
        System.out.println("    " + tamanho);
        System.out.println("    " + tipoEmbalagem.toString());
        System.out.println("    R$" + precoUnitario);
    }

    public void venda(){
        Restaurante.adicionarCaixa(precoUnitario - precoCusto);
    }
}
