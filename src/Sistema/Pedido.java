package Sistema;
import Enums.FormaPagamentoEnum;
import Excecoes.IngredientesInsuficientes;
import Itens.*;
import Funcionarios.*;

import javax.naming.CompositeName;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static Sistema.Restaurante.diaSemana;

public class Pedido {
    private ArrayList<Item> itensPedido = new ArrayList<Item>();
    private Cozinheiro cozinheiroPrincipal;
    private Cozinheiro cozinheiroSobremesa;
    private Garcom garcom;
    private int mesa;
    private double valorTotal = 0;
    private LocalDateTime dataHoraRegistro;
    private LocalDateTime dataHoraPagamento;
    private FormaPagamentoEnum pagamento;
    private static int limitador_pratos_principais = 10;
    private static int limitador_sobremesas = 4;
    private static int limitador_bebidas = 8;

    public Pedido(ArrayList<Item> itensPedido, Garcom garcom, int mesa) throws IngredientesInsuficientes {
        this.itensPedido.addAll(itensPedido);

        for(Item i : itensPedido){
            i.venda();
        }

        escolherCozinheiros();
        this.garcom = garcom;
        this.dataHoraRegistro = Restaurante.dataCentral;

        garcom.pedidoFeito();
        cozinheiroPrincipal.pedidoFeito();
        cozinheiroSobremesa.pedidoFeito();
        this.mesa = mesa;

        System.out.println("Pedido registrado com sucesso!");
    }

    public Pedido(Garcom garcom){
        this.garcom = garcom;
        this.dataHoraRegistro = Restaurante.dataCentral;
    }


    public void adicionaPedido(ArrayList<Item> itensPedido) throws IngredientesInsuficientes{
        for(Item i : itensPedido) {
            i.venda();
        }
        this.itensPedido.addAll(itensPedido);
    }

    public void mostrar(){
        System.out.println("Itens: ");
        for(Item i : itensPedido){
            System.out.println("    " + i.getNome());
        }
        System.out.println("Cozinheiro Responsável pelos Pratos Principais: ");
        cozinheiroPrincipal.mostrar();
        System.out.println("Cozinheiro Responsável pelas Sobremesas: ");
        cozinheiroSobremesa.mostrar();
        System.out.println("Garçom responsável: ");
        garcom.mostrar();
        calculaTotal();
        System.out.println("Valor: " + valorTotal);
        System.out.println("Data e Hora de Registro: " + dataHoraRegistro.toString());
        System.out.println("Data e Hora e Forma do pagamento: " + dataHoraPagamento.toString() + pagamento);

    }

    public void mostrarSimples(){

        System.out.println("Itens: ");
        for(Item i : itensPedido){
            System.out.println("    " + i.getNome());
        }
        System.out.println("Garçom responsável: ");
        garcom.mostrar();
        calculaTotal();
        System.out.println("Valor total: " + valorTotal);

    }

    public void calculaTotal() {
        for (Item i : itensPedido) {
            valorTotal += i.getPrecoUnitario();
        }
    }

    public void pagarConta(FormaPagamentoEnum pagamento){
        this.dataHoraPagamento = Restaurante.dataCentral;
        this.pagamento = pagamento;
    }

    public void geraPedido(){
        Random rand = new Random();

        int numPratosPrincipais = rand.nextInt(limitador_pratos_principais);
        for(int i = 0; i < numPratosPrincipais; i++){
            PratoPrincipal p = Restaurante.pratosPrincipais.get(rand.nextInt(Restaurante.pratosPrincipais.size()));
            itensPedido.add(p);
        }

        int numSobremesas = rand.nextInt(limitador_sobremesas);
        for(int i = 0; i < numSobremesas; i++){
            Sobremesa s = Restaurante.sobremesas.get(rand.nextInt(Restaurante.sobremesas.size()));
            itensPedido.add(s);
        }

        int numBebidas = rand.nextInt(limitador_bebidas);
        for(int i = 0; i < numBebidas; i++){
            Bebida b = Restaurante.bebidas.get(rand.nextInt(Restaurante.bebidas.size()));
            itensPedido.add(b);
        }
        escolherCozinheiros();
    }

    public void escolherCozinheiros(){
        System.out.println("Qual será o chef responsável pelos pratos principais?");
        int i = 0, j = 0;
        for(Cozinheiro c : Restaurante.cozinheiros){
            if(c.isPratoEspecializado()){
                System.out.println("(" + i+1 + ") " + c.getNome());
                i++;
                j++;
            }
        }
        Scanner sc = new Scanner(System.in);
        cozinheiroPrincipal = Restaurante.cozinheiros.get(sc.nextInt() + j+1);

        System.out.println("Qual será o chef responsável pelas sobremesas?");
        i = j = 0;
        for(Cozinheiro c : Restaurante.cozinheiros){
            if(!c.isPratoEspecializado()){
                System.out.println("(" + i+1 + ") " + c.getNome());
                i++;
                j++;
            }
        }
        cozinheiroSobremesa = Restaurante.cozinheiros.get(sc.nextInt() + j+1);
    }

    public void venda() throws IngredientesInsuficientes{
        for(Item i : itensPedido){
            i.venda();
        }
    }

    public int getMesa(){
        return mesa;
    }
}
