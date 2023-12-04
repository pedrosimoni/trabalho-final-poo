package Itens;
import java.util.ArrayList;


public class PratoPrincipal extends Prato implements PodeVender{
    public PratoPrincipal(String nome, String codigo, double precoUnitario, ArrayList<Ingrediente> ingredientes, String descricao, String tempoPreparo){

        super(nome, codigo, precoUnitario, ingredientes, descricao, tempoPreparo);
        venda();
    }


}
