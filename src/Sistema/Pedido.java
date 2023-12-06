package Sistema;
import Enums.FormaPagamentoEnum;
import Itens.*;
import Funcionarios.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static Sistema.Restaurante.diaSemana;

public class Pedido {
    private ArrayList<Item> itensPedido = new ArrayList<Item>();//
    private Cozinheiro cozinheiro;
    private Garcom garcom;//
    private double valorTotal = 0;
    private LocalDateTime dataHoraRegistro;
    private LocalDateTime dataHoraPagamento;
    private FormaPagamentoEnum pagamento;
    private static int limitador_pratos_principais = 10;
    private static int limitador_sobremesas = 4;
    private static int limitador_bebidas = 8;

    public Pedido(Item []itensPedido, Cozinheiro cozinheiro, Garcom garcom,  String data){
        this.itensPedido.addAll(Arrays.asList(itensPedido));

        for(Item i : itensPedido){
            i.venda();
        }

        this.cozinheiro = cozinheiro;
        this.garcom = garcom;
        this.dataHoraRegistro = Restaurante.dataCentral;

        garcom.pedidoFeito();
        cozinheiro.pedidoFeito();

        System.out.println("Pedido registrado com sucesso!");
    }

    public Pedido(Cozinheiro cozinheiro, Garcom garcom, String data){
        this.cozinheiro = cozinheiro;
        this.garcom = garcom;
        this.dataHoraRegistro = Restaurante.dataCentral;
    }

    public Pedido(){
        this.cozinheiro = null;
        this.garcom = null;
        this.dataHoraRegistro = Restaurante.dataCentral;
        this.pagamento = null;
    }

    public void adicionaPedido(Item []itensPedidos){
        for(Item i : itensPedidos){
            i.venda();
        }
        this.itensPedido.addAll(Arrays.asList((itensPedidos)));
    }

    public void mostrar(){
        System.out.println("Itens: ");
        for(Item i : itensPedido){
            System.out.println("    " + i.getNome());
        }
        System.out.println("Cozinheiro Responsável: ");
        cozinheiro.mostrar();
        System.out.println("Garçom responsável: ");
        garcom.mostrar();
        System.out.println("Valor total: " + valorTotal);
        System.out.println("Data e Hora de Registro: " + dataHoraRegistro.toString());
        System.out.println("Data e Hora e Forma do pagamento: " + dataHoraPagamento.toString() + pagamento);

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

        garcom = Restaurante.garcons.get(rand.nextInt(Restaurante.garcons.size()));
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
        cozinheiro = Restaurante.cozinheiros.get(sc.nextInt() + j+1);

        System.out.println("Qual será o chef responsável pelas sobremesas?");
        i = j = 0;
        for(Cozinheiro c : Restaurante.cozinheiros){
            if(!c.isPratoEspecializado()){
                System.out.println("(" + i+1 + ") " + c.getNome());
                i++;
                j++;
            }
        }
        cozinheiro = Restaurante.cozinheiros.get(sc.nextInt() + j+1);
    }
}
