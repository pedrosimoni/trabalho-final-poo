import Excecoes.CPFInvalido;
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
        int op, tamanho, qtd,ano,mes,dia;
        Scanner sc = new Scanner(System.in);
        String nome, codigo, descricao, tempoPreparo, estadoCivil, endereco, diaFolga, tipoEmbalagem,inputOp;
        double precoUnitario, salarioBase, calorias, precoCusto;
        ArrayList<Ingrediente> lista = new ArrayList<Ingrediente>();
        long cpf, rg, numCarteira;
        boolean pratoEspecializado,repeteLoopMenu = true,repetExcessoes;
        LocalDate dataAdimissão;

        System.out.println("Bem vindo ao sistema!");
        do {
            System.out.println("1 -  Cadastrar novo prato principal                                 (R$" + Restaurante.caixa + ") Caixa");
            System.out.println("2 -  Cadastrar nova sobremesa                                       (" + Restaurante.pedidosEsperandoAprovacao.size() + ") Pedidos");
            System.out.println("3 -  Cadastrar novo garçom");
            System.out.println("4 -  Cadastrar novo cozinheiro");
            System.out.println("5 -  Cadastrar nova bebida");
            System.out.println("6 -  Cadastrar novo ingrediente");
            System.out.println("7 -  Fazer Compras");
            System.out.println("8 -  Olhar estoque de ingredientes");
            System.out.println("9 -  Olhar cardápio");
            System.out.println("10 - Ver histórico de pedidos do mês");
            System.out.println("11 - Calcular e mostrar salários dos funcionários");
            System.out.println("12 - Sair");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.print("\nDigite o nome de prato: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o código do prato: ");
                    codigo = sc.nextLine();
                    System.out.print("\nDigite a descricao do prato: ");
                    descricao = sc.nextLine();
                    System.out.print("\nDigite o tempo de preparo do prato(hh:mm): ");
                    tempoPreparo = sc.nextLine();
                    System.out.print("\nDigite o precoUnitario do prato: ");
                    precoUnitario = sc.nextFloat();
                    sc.nextLine();
                    System.out.println("\nEscolha os ingredientes do prato: ");
                    for (Ingrediente i : Restaurante.estoque) {
                        System.out.printf(i.getNome() + "(s/n)");
                        inputOp = sc.nextLine();
                        if (inputOp.charAt(0) == 's' || inputOp.charAt(0) == 'S') {
                            lista.add(i);
                        }
                    }
                    PratoPrincipal p = new PratoPrincipal(nome, codigo, precoUnitario, lista, descricao, tempoPreparo);
                    p.calculaPrecoCusto();
                    lista.clear();
                    Restaurante.pratosPrincipais.add(p);
                }
                case 2 -> {
                    System.out.print("\nDigite o nome da sobremesa: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o código da sobremesa: ");
                    codigo = sc.nextLine();
                    System.out.print("\nDigite a descricao da sobremesa: ");
                    descricao = sc.nextLine();
                    System.out.print("\nDigite o tempo de preparo da sobremesa(hh:mm): ");
                    tempoPreparo = sc.nextLine();
                    System.out.print("\nDigite o precoUnitario da sobremesa: ");
                    precoUnitario = sc.nextFloat();
                    sc.nextLine();
                    System.out.println("\nEscolha os ingredientes da sobremesa: ");
                    for (Ingrediente i : Restaurante.estoque) {
                        System.out.printf(i.getNome() + "(s/n)");
                        inputOp = sc.nextLine();
                        if (inputOp.charAt(0) == 's' || inputOp.charAt(0) == 'S') {
                            lista.add(i);
                        }
                    }
                    System.out.print("\nDigite a quantidade de calorias na sobremesa: ");
                    calorias = sc.nextFloat();
                    Sobremesa s = new Sobremesa(nome, codigo, precoUnitario, lista, descricao, tempoPreparo, calorias);
                    s.calculaPrecoCusto();
                    lista.clear();
                    Restaurante.sobremesas.add(s);
                }
                case 3 -> {
                    System.out.print("\nDigite o nome do novo garçom: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o CPF do novo garçom: ");
                    cpf = sc.nextLong();
                    System.out.print("\nDigite o RG do novo garçom: ");
                    rg = sc.nextLong();
                    sc.nextLine();
                    System.out.print("\nDigite o Estado Civil do novo garçom: ");
                    estadoCivil = sc.nextLine();
                    System.out.print("\nDigite o Endereço do novo garçom: ");
                    endereco = sc.nextLine();
                    System.out.print("\nDeseja considerar hoje como a Aata de Adimissão do novo garçom? (s/n)");
                    inputOp = sc.nextLine();
                    if (inputOp.charAt(0) == 's' || inputOp.charAt(0) == 'S') {
                        dataAdimissão = Restaurante.dataCentral.toLocalDate();
                    } else {
                        dataAdimissão = null;
                        do {
                            repetExcessoes = false;
                            try {
                                System.out.print("\nDigite a Data de Adimissão (ddmmaa): ");
                                System.out.print("Ano: ");
                                ano = sc.nextInt();
                                System.out.print("Mes: ");
                                mes = sc.nextInt();
                                System.out.print("Dia: ");
                                dia = sc.nextInt();
                                sc.nextLine();
                                dataAdimissão = LocalDate.of(ano, mes, dia);
                            } catch (DateTimeException d) {
                                System.out.println("Você digitou uma data inválida, por favor digite novamente");
                                repetExcessoes = true;
                            }
                        }while(repetExcessoes);
                    }
                    System.out.print("\nDigite o Número da Carteira do novo garçom: ");
                    numCarteira = sc.nextLong();
                    sc.nextLine();
                    System.out.print("\nDigite o dia de folga do novo garçom: ");
                    diaFolga = sc.nextLine();
                    System.out.print("Digite o Salário Base do novo garçom: ");
                    salarioBase = sc.nextFloat();
                    Garcom g = null;
                    do{
                        repetExcessoes = false;
                        try {
                            g = new Garcom(nome, cpf, rg, estadoCivil, endereco, dataAdimissão, numCarteira, diaFolga, salarioBase);
                        } catch (CPFInvalido f) {
                            System.out.println("Você digitou um cpf errado, por favor digite novamente: ");
                            cpf = sc.nextLong();
                            repetExcessoes = true;
                        }
                    }while(repetExcessoes);

                    g.calculaSalario();
                    Restaurante.garcons.add(g);
                }
                case 4 -> {
                    System.out.print("\nDigite o nome do novo cozinheiro: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o CPF do novo cozinheiro: ");
                    cpf = sc.nextLong();
                    System.out.print("\nDigite o RG do novo cozinheiro: ");
                    rg = sc.nextLong();
                    sc.nextLine();
                    System.out.print("\nDigite o Estado Civil do novo cozinheiro: ");
                    estadoCivil = sc.nextLine();
                    System.out.print("\nDigite o Endereço do novo cozinheiro: ");
                    endereco = sc.nextLine();
                    System.out.print("\nDeseja considerar hoje como a Aata de Adimissão do novo cozinheiro? (s/n)");
                    inputOp = sc.nextLine();
                    if (inputOp.charAt(0) == 's' || inputOp.charAt(0) == 'S') {
                        dataAdimissão = Restaurante.dataCentral.toLocalDate();
                    } else {
                        dataAdimissão = null;
                        do {
                            repetExcessoes = false;
                            try {
                                System.out.print("\nDigite a Data de Adimissão (ddmmaa): ");
                                System.out.print("Ano: ");
                                ano = sc.nextInt();
                                System.out.print("Mes: ");
                                mes = sc.nextInt();
                                System.out.print("Dia: ");
                                dia = sc.nextInt();
                                sc.nextLine();
                                dataAdimissão = LocalDate.of(ano, mes, dia);
                            } catch (DateTimeException d) {
                                System.out.println("Você digitou uma data inválida, por favor digite novamente");
                                repetExcessoes = true;
                            }
                        }while(repetExcessoes);

                    }
                    System.out.print("\nDigite o Número da Carteira do novo cozinheiro: ");
                    numCarteira = sc.nextLong();
                    System.out.print("\nO novo cozinheiro é especializado em Pratos Principais(1) ou Sobremesas(2)?\n ");
                    pratoEspecializado = sc.nextInt() != 2;
                    Cozinheiro c = null;
                    do{
                        repetExcessoes = false;
                        try {
                            c = new Cozinheiro(nome, cpf, rg, estadoCivil, endereco, dataAdimissão, numCarteira, pratoEspecializado);
                        } catch (CPFInvalido f) {
                            System.out.print("Você digitou um cpf errado, por favor digite novamente: ");
                            cpf = sc.nextLong();
                            repetExcessoes = true;
                        }
                    }while(repetExcessoes);

                    c.calculaSalario();
                    Restaurante.cozinheiros.add(c);
                }
                case 5 -> {
                    System.out.print("\nDigite o Nome da nova bebida: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o Codigo da nova bebida: ");
                    codigo = sc.nextLine();
                    System.out.print("\nDigite o Preço da nova bebida: ");
                    precoUnitario = sc.nextDouble();
                    System.out.print("\nDigite o Preço de Custo da nova bebida: ");
                    precoCusto = sc.nextDouble();
                    System.out.print("\nDigite o Tamanho da nova bebida: ");
                    tamanho = sc.nextInt();
                    System.out.print("\nQual o tipo de Embalagem da nova bebida: ");
                    System.out.print("\nLata(1)\nGarrafa Plástica(2)\nGarrafa de Vidro(3)\nRefil(4)\n:->");
                    switch (sc.nextInt()) {
                        default -> tipoEmbalagem = "LATA";
                        case 2 -> tipoEmbalagem = "GARRAFA_PLASTICA";
                        case 3 -> tipoEmbalagem = "GARRAFA_VIDRO";
                        case 4 -> tipoEmbalagem = "COPO_REFIL";
                    }
                    Bebida b = new Bebida(nome, codigo, precoUnitario, precoCusto, tamanho, tipoEmbalagem);
                    Restaurante.bebidas.add(b);
                }
                case 6 -> {
                    System.out.print("\nDigite o Nome do novo ingrediente: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite quantos ingredientes devem ser cadastrados: ");
                    qtd = sc.nextInt();
                    System.out.print("\nDigite o Preço de Custo do novo ingrediente: ");
                    precoCusto = sc.nextDouble();
                    Ingrediente i = new Ingrediente(nome, qtd, precoCusto);
                    Restaurante.estoque.add(i);
                }
                case 7 -> {
                    System.out.print("\nPressione 'Enter' para não comprar e a quantidade desejada para comprar: \n");
                    for (Ingrediente in : Restaurante.estoque) {
                        System.out.print("\n" + in.getNome() + ": ");
                        in.compra(sc.nextInt());
                    }
                    System.out.print("\nObrigado por comprar!");
                }
                case 8 -> {
                    for (Ingrediente in : Restaurante.estoque) {
                        System.out.print("\n" + in.getNome());
                    }
                    System.out.println();
                }
                case 9 -> {

                    Restaurante.menu();

                }
                case 10 -> {
                    for (Pedido pn : Restaurante.pedidosMensais) {
                        pn.mostrar();
                    }
                }
                case 11 -> {

                    Restaurante.mostrarCozinheiros();
                    Restaurante.mostrarGarcons();
                }
                case 12->{
                    repeteLoopMenu = false;
                }
            }
        } while (repeteLoopMenu);
        pers.gravaBase();
        pers.fecharArquivos();
    }
}