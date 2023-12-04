package Itens;
import java.util.ArrayList;

public class Sobremesa extends Prato implements PodeVender{
    private double numCalorias;

    public Sobremesa(String nome, String codigo, double precoUnitario, ArrayList<Ingrediente> ingredientes, String descricao, String tempoPreparo, double numCalorias){
        super(nome, codigo, precoUnitario, ingredientes, descricao, tempoPreparo);
        venda();
        this.numCalorias = numCalorias;
    }

}
