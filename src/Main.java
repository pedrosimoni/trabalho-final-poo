import Enums.EmbalagensEnum;
import Excecoes.CPFInvalido;
import Excecoes.IngredientesInsuficientes;
import Sistema.*;
import Funcionarios.*;
import Itens.*;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Timer t = new Timer();
        Persistencia pers = new Persistencia();
        pers.leBase();
        TimerTask tt = new TimerTask() {
            public void run() {
                if(Restaurante.dataCentral.getHour() == 0){
                    Restaurante.setDiaSemana();
                    //System.out.println("Hoje é dia " + Restaurante.dataCentral.getDayOfMonth() + ", " + Restaurante.diaSemana.toString());
                    if (Restaurante.dataCentral.getDayOfMonth() == Restaurante.dataCentral.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
                        System.out.println("Fim de mês!!");

                        if (Garcom.numPedidos > Garcom.metaPedidos) {
                            Garcom.bateuMeta = true;
                            System.out.println("Os garçons bateram a meta de pedidos feitos esse mês!!");
                        } else {
                            Garcom.bateuMeta = false;
                            System.out.println("Infelizmente os garçons não bateram a meta esse mês!!");
                        }

                        Garcom.numPedidos = 0;
                        for (Garcom g : Restaurante.garcons) {
                            g.calculaSalario();
                        }

                        for (Cozinheiro c : Restaurante.cozinheiros) {
                            c.calculaSalario();
                            c.setNumPratos(0);
                        }
                    }
                }
                Restaurante.dataCentral = Restaurante.dataCentral.plusHours(1);
                //System.out.println("Hora atual = " + Restaurante.dataCentral.getHour());
            }
        };
        t.scheduleAtFixedRate(tt, 0, 1000);

        /*
        TimerTask tn = new TimerTask(){
            public void run(){
                System.out.println("Eba novo pedido!");
            }
        };
        t.scheduleAtFixedRate(tn, 1000, 1000);
        */

        boolean repeteLoopMenu = true;
        System.out.println("Bem vindo ao sistema!");
        do {

            int op;
            Scanner sc = new Scanner(System.in);
            ArrayList<Item> listaItens = new ArrayList<Item>();

            System.out.println("N/A -  Aprovar pedidos                               (R$" + Restaurante.caixa + ") Caixa");
            System.out.println(" 1  -  Fazer compras                                 (" + Restaurante.pedidosEsperandoAprovacao.size() + ") Pedidos");
            System.out.println(" 2  -  Adicionar pedido manualmente");
            System.out.println(" 3  -  Adicionar itens a um pedido");
            System.out.println(" 4  -  Cadastrar/Remover itens");
            System.out.println(" 5  -  Conferir restaurante");
            System.out.println(" 6  -  Sair");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {

                    System.out.print("\nPressione 'Enter' para não comprar e a quantidade desejada para comprar: \n");
                    for (Ingrediente in : Restaurante.estoque) {
                        System.out.print("\n" + in.getNome() + ": ");
                        in.compra(sc.nextInt());
                    }
                    System.out.print("\nObrigado por comprar!");

                }
                case 2 -> {

                    System.out.print("\nPressione 'x' para adicionar o prato: \n");
                    listaItens.clear();
                    for (PratoPrincipal p : Restaurante.pratosPrincipais){
                        System.out.print("\n - " + p.getNome() + ": ");
                        if(sc.nextLine().equals("x")){
                            listaItens.add(p);
                        }
                    }

                    System.out.print("\nAgora escolha o garçom: ");
                    Garcom gt = null;
                    for(Garcom g : Restaurante.garcons) {
                        if (g.getDiaFolga() != Restaurante.diaSemana) {
                            System.out.print("\n - " + g.getNome() + ": ");
                            if (sc.nextLine().equals("x")) {
                                gt = g;
                                break;
                            }
                        }
                    }
                    if(gt == null) break;

                    System.out.print("\nDigite a mesa do pedido: ");
                    int mesa = sc.nextInt();
                    while(mesa > Restaurante.mesaMAX){
                        System.out.println("Digite uma mesa entre 1 e " + Restaurante.mesaMAX + ": ");
                        mesa = sc.nextInt();
                    }

                    try{
                        Pedido p = new Pedido(listaItens, gt, mesa);
                        Restaurante.pedidosAbertos.add(p);
                    }catch (IngredientesInsuficientes i){
                        System.out.print("\nPedido não efetuado!");
                    }

                }
                case 3 -> {

                    System.out.print("\nPressione 'x' para escolher um pedido: ");
                    for(Pedido p : Restaurante.pedidosAbertos){
                        System.out.print("\n- Mesa " + p.getMesa() + ": ");
                        if(sc.nextLine().equals("x")){
                            System.out.print("\nPressione 'x' para adicionar o prato: \n");
                            listaItens.clear();
                            for (PratoPrincipal pn : Restaurante.pratosPrincipais){
                                System.out.print("\n - " + pn.getNome() + ": ");
                                if(sc.nextLine().equals("x")){
                                    listaItens.add(pn);
                                }
                            }

                            try{
                                p.adicionaPedido(listaItens);
                            }catch (IngredientesInsuficientes i){
                                System.out.print("\nMudança não efetuada!");
                            }
                        }
                    }

                }
                case 4 -> {

                    cadastrarXremover();

                }
                case 5 -> {

                    conferir();

                }

                case 6 -> {

                    repeteLoopMenu = false;

                }
                default -> {

                    System.out.print("\nPressione 'Enter' para aprovar um pedido: ");
                    int tamanho = Restaurante.pedidosEsperandoAprovacao.size();
                    for(int i = 0; i < tamanho ; i++){
                        Pedido p = Restaurante.pedidosEsperandoAprovacao.pop();
                        p.mostrarSimples();
                        if(sc.nextLine().equals("x")){
                            listaItens.clear();
                            listaItens = geraListaItens();
                            try{
                                p.adicionaPedido(listaItens);
                                Restaurante.pedidosAbertos.add(p);
                            }catch (IngredientesInsuficientes ii){
                                System.out.print("\nPedido não aprovado!");
                            }
                        }
                    }
                }
            }
        } while (repeteLoopMenu);
        pers.gravaBase();
        pers.fecharArquivos();
    }

    public static void cadastrarXremover(){
        boolean repeteLoopMenu = true;
        do{

            int op;
            Scanner sc = new Scanner(System.in);

            System.out.println("1 - Adicionar/remover prato principal");
            System.out.println("2 - Adicionar/remover sobremesa");
            System.out.println("3 - Adicionar/remover bebida");
            System.out.println("4 - Cadastrar/remover garçom");
            System.out.println("5 - Cadastrar/remover cozinheiro");
            System.out.println("6 - Cadastrar/remover ingrediente");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();

            switch(op) {
                case 1 -> {
                    System.out.print("\nDeseja adicionar um prato(a) ou remover um prato(r): ");
                    String opc = sc.nextLine();
                    ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();

                    if (opc.equals("a")) {

                        System.out.print("\nDigite o nome de prato: ");
                        String nome = sc.nextLine();

                        System.out.print("\nDigite o código do prato: ");
                        String codigo = sc.nextLine();

                        System.out.print("\nDigite a descricao do prato: ");
                        String descricao = sc.nextLine();

                        System.out.print("\nDigite o tempo de preparo do prato(hh:mm): ");
                        String tempoPreparo = sc.nextLine();

                        System.out.print("\nDigite o precoUnitario do prato: ");
                        Float precoUnitario = sc.nextFloat();
                        sc.nextLine();

                        System.out.println("\nPressione 'Enter' para não adicionar o ingrediente e 'x' para adicionar: ");
                        listaIngredientes.clear();
                        for (Ingrediente i : Restaurante.estoque) {
                            System.out.printf("\n- " + i.getNome() + ": ");
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                listaIngredientes.add(i);
                            }
                        }

                        PratoPrincipal p = new PratoPrincipal(nome, codigo, precoUnitario, listaIngredientes, descricao, tempoPreparo);
                        p.calculaPrecoCusto();
                        Restaurante.pratosPrincipais.add(p);

                    } else if (opc.equals("r")) {

                        System.out.println("\nPressione 'Enter' para não remover o prato e 'x' para remover: ");
                        for (PratoPrincipal p : Restaurante.pratosPrincipais) {
                            System.out.printf("\n- " + p.getNome() + ": ");
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                Restaurante.pratosPrincipais.remove(p);
                            }
                        }
                    }

                }
                case 2 -> {

                    System.out.print("\nDeseja adicionar uma nova sobremesa(a) ou remover uma sobremesa(r): ");
                    String opc = sc.nextLine();
                    ArrayList<Ingrediente> listaIngredientes = new ArrayList<Ingrediente>();

                    if (opc.equals("a")) {

                        System.out.print("\nDigite o nome da sobremesa: ");
                        String nome = sc.nextLine();

                        System.out.print("\nDigite o código da sobremesa: ");
                        String codigo = sc.nextLine();

                        System.out.print("\nDigite a descricao da sobremesa: ");
                        String descricao = sc.nextLine();

                        System.out.print("\nDigite o tempo de preparo da sobremesa(hh:mm): ");
                        String tempoPreparo = sc.nextLine();

                        System.out.print("\nDigite o precoUnitario da sobremesa: ");
                        Float precoUnitario = sc.nextFloat();
                        sc.nextLine();

                        System.out.println("\nPressione 'Enter' para não adicionar o ingrediente e 'x' para adicionar: ");
                        listaIngredientes.clear();
                        for (Ingrediente i : Restaurante.estoque) {
                            System.out.printf("\n- " + i.getNome() + ": ");
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                listaIngredientes.add(i);
                            }
                        }

                        System.out.print("\nDigite a quantidade de calorias na sobremesa: ");
                        Double calorias = sc.nextDouble();

                        Sobremesa s = new Sobremesa(nome, codigo, precoUnitario, listaIngredientes, descricao, tempoPreparo, calorias);
                        s.calculaPrecoCusto();
                        Restaurante.sobremesas.add(s);

                    } else if (opc.equals("r")) {

                        System.out.println("\nPressione 'Enter' para não remover a sobremesa e 'x' para remover: ");
                        for (Sobremesa s : Restaurante.sobremesas) {
                            System.out.printf("\n- " + s.getNome() + ": ");
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                Restaurante.sobremesas.remove(s);
                            }
                        }

                    }

                }
                case 3 -> {

                    System.out.print("\nDeseja adicionar uma nova bebida(a) ou remover uma bebida(r): ");
                    String opc = sc.nextLine();

                    if (opc.equals("a")) {

                        System.out.print("\nDigite o Nome da nova bebida: ");
                        String nome = sc.nextLine();

                        System.out.print("\nDigite o Codigo da nova bebida: ");
                        String codigo = sc.nextLine();

                        System.out.print("\nDigite o Preço da nova bebida: ");
                        Double precoUnitario = sc.nextDouble();

                        System.out.print("\nDigite o Preço de Custo da nova bebida: ");
                        Double precoCusto = sc.nextDouble();

                        System.out.print("\nDigite o Tamanho da nova bebida: ");
                        int tamanho = sc.nextInt();

                        System.out.print("\nQual o tipo de Embalagem da nova bebida: ");
                        System.out.print("\nLata(1)\nGarrafa Plástica(2)\nGarrafa de Vidro(3)\nRefil(4)\n:->");
                        String tipoEmbalagem;
                        switch (sc.nextInt()) {
                            default -> tipoEmbalagem = "LATA";
                            case 2 -> tipoEmbalagem = "GARRAFA_PLASTICA";
                            case 3 -> tipoEmbalagem = "GARRAFA_VIDRO";
                            case 4 -> tipoEmbalagem = "COPO_REFIL";
                        }

                        Bebida b = new Bebida(nome, codigo, precoUnitario, precoCusto, tamanho, tipoEmbalagem);
                        Restaurante.bebidas.add(b);

                    } else if (opc.equals("x")) {

                        System.out.println("\nPressione 'Enter' para não remover a bebida e 'x' para remover: ");
                        for (Bebida b : Restaurante.bebidas) {

                            b.mostrarBasico();
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                Restaurante.sobremesas.remove(b);
                            }

                        }

                    }

                }
                case 4 -> {}
            }
        }while(repeteLoopMenu);
    }

    public static void conferir(){

    }
}