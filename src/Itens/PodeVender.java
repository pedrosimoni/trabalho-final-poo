package Itens;

import Excecoes.IngredientesInsuficientes;

public interface PodeVender {
    public void venda() throws IngredientesInsuficientes;
}
