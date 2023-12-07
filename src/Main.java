import Enums.DiasEnum;
import Enums.EmbalagensEnum;
import Enums.FormaPagamentoEnum;
import Excecoes.CPFInvalido;
import Excecoes.SaldoInsuficiente;
import Excecoes.IngredientesInsuficientes;
import Sistema.*;
import Funcionarios.*;
import Itens.*;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static Enums.FormaPagamentoEnum.*;


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
                        Restaurante.balancoMensal = true;
                    }
                }
                Restaurante.dataCentral = Restaurante.dataCentral.plusMinutes(5);
                //System.out.println("Hora atual = " + Restaurante.dataCentral.getHour());
            }
        };
        t.scheduleAtFixedRate(tt, 0, 83);


        TimerTask tn = new TimerTask(){
            Pedido pedidoTimerTask;
            public void run(){
                System.out.println("Eba novo pedido!");
                pedidoTimerTask = new Pedido();
                Restaurante.pedidosAbertos.add(pedidoTimerTask);
            }
        };
        t.scheduleAtFixedRate(tn, 0, 60000);


        boolean repeteLoopMenu = true;
        System.out.println("Bem vindo ao sistema!");
        do {

            int op;
            Scanner sc = new Scanner(System.in);
            ArrayList<Item> listaItens = new ArrayList<Item>();

            System.out.println("\nN/A -  Aprovar pedidos                               (R$" + Restaurante.caixa + ") Caixa");
            System.out.println(" 1  -  Fazer compras                                   (" + Restaurante.pedidosEsperandoAprovacao.size() + ") Pedidos");
            System.out.println(" 2  -  Dar baixa em um pedido");
            System.out.println(" 3  -  Adicionar pedido manualmente");
            System.out.println(" 4  -  Adicionar itens a um pedido");
            System.out.println(" 5  -  Cadastrar/Remover itens");
            System.out.println(" 6  -  Conferir restaurante");
            System.out.println(" 7  -  Operações financeiras");
            System.out.println(" 8  -  Sair");
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

                    System.out.print("\nPressione 'x' para escolher um pedido: ");
                    for (Pedido p : Restaurante.pedidosAbertos) {
                        System.out.print("\n- Mesa " + p.getMesa() + ": ");
                        if (sc.nextLine().equals("x")) {
                            System.out.print("\nEscolha uma forma de pagamento: ");
                            System.out.print("\n(1) Dinheiro / (2) Débito / (3) Crédito");
                            int opt = sc.nextInt();
                            if(opt == 1){
                                p.pagarConta(DINHEIRO);
                            }else if(opt == 2){
                                p.pagarConta(DEBITO);
                            }else if(opt == 3){
                                p.pagarConta(CREDITO);
                            }

                            break;
                        }


                    }
                }


                case 3 -> {
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
                case 4 -> {
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
                case 5 -> {
                    cadastrarXremover();
                }
                case 6 -> {
                    conferir();
                }
                case 7 -> {
                    boolean repeteLoopMenuInterno = true;
                    do {
                        int opi;

                        System.out.println("1 - Pagar dívidas");
                        System.out.println("2 - Ver o balanço mensal");
                        System.out.println("3 - Operações de caixa");
                        System.out.println("Digite qualquer outra tecla para sair.");
                        System.out.print("Escolha uma opção: ");
                        opi = sc.nextInt();
                        sc.nextLine();

                        switch (opi) {
                            case 1 -> {
                                pagarDividas();
                            }
                            case 2 -> {
                                balnçoMensal();
                            }
                            case 3 -> {
                                operacoesDeCaixa();
                            }
                            default -> {
                                repeteLoopMenuInterno = false;
                            }
                        }
                    }while(repeteLoopMenuInterno);
                }
                default -> {

                    System.out.print("\nPressione 'Enter' para aprovar um pedido: ");
                    int tamanho = Restaurante.pedidosEsperandoAprovacao.size();
                    for(int i = 0; i < tamanho ; i++){
                        Pedido p = Restaurante.pedidosEsperandoAprovacao.pop();
                        p.mostrarSimples();
                        if(sc.nextLine().equals("x")){
                            try{
                                p.geraPedido();
                                p.venda();
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

    private static void cadastrarXremover(){
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
            System.out.println("Digite qualquer outra tecla para sair.");
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
                case 4 -> {

                    System.out.print("\nDeseja cadastrar um novo garçom(a) ou remover um garçom(r): ");
                    String opc = sc.nextLine();

                    if(opc.equals("a")){
                        System.out.print("\nDigite o nome do novo garçom: ");
                        String nome = sc.nextLine();

                        System.out.print("\nDigite o CPF do novo garçom: ");
                        Long cpf = sc.nextLong();

                        System.out.print("\nDigite o RG do novo garçom: ");
                        Long rg = sc.nextLong();
                        sc.nextLine();

                        System.out.print("\nDigite o Estado Civil do novo garçom: ");
                        String estadoCivil = sc.nextLine();

                        System.out.print("\nDigite o Endereço do novo garçom: ");
                        String endereco = sc.nextLine();

                        System.out.print("\nDeseja considerar hoje como a Data de Adimissão do novo garçom? (s/n)");
                        String inputOp = sc.nextLine();
                        LocalDate dataAdmissao = null;
                        if (inputOp.charAt(0) == 's' || inputOp.charAt(0) == 'S') {
                            dataAdmissao = Restaurante.dataCentral.toLocalDate();
                        } else {
                            boolean repetExcessoes = false;
                            do {
                                try {
                                    System.out.print("\nDigite a Data de Adimissão (ddmmaa): ");
                                    System.out.print("Ano: ");
                                    int ano = sc.nextInt();
                                    System.out.print("Mes: ");
                                    int mes = sc.nextInt();
                                    System.out.print("Dia: ");
                                    int dia = sc.nextInt();
                                    sc.nextLine();
                                    dataAdmissao = LocalDate.of(ano, mes, dia);
                                } catch (DateTimeException d) {
                                    System.out.println("Você digitou uma data inválida, por favor digite novamente");
                                    repetExcessoes = true;
                                }
                            }while(repetExcessoes);
                        }

                        System.out.print("\nDigite o Número da Carteira do novo garçom: ");
                        Long numCarteira = sc.nextLong();
                        sc.nextLine();

                        System.out.print("\nDigite o dia de folga do novo garçom: ");
                        String diaFolga = sc.nextLine();

                        System.out.print("Digite o Salário Base do novo garçom: ");
                        Double salarioBase = sc.nextDouble();

                        boolean repetExcessoes = false;
                        Garcom g = null;
                        do{
                            try {
                                g = new Garcom(nome, cpf, rg, estadoCivil, endereco, dataAdmissao, numCarteira, diaFolga, salarioBase);
                            } catch (CPFInvalido f) {
                                System.out.println("Você digitou um cpf errado, por favor digite novamente: ");
                                cpf = sc.nextLong();
                                repetExcessoes = true;
                            }
                        }while(repetExcessoes);

                        g.calculaSalario();
                        Restaurante.garcons.add(g);

                    }else if(opc.equals("r")){

                        System.out.println("\nPressione 'Enter' para não remover o garçom e 'x' para remover: ");

                        for (Garcom g : Restaurante.garcons) {

                            g.mostrar();
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                Restaurante.garcons.remove(g);
                            }

                        }

                    }

                }
                case 5 -> {

                    System.out.print("\nDeseja cadastrar um novo cozinheiro(a) ou remover um cozinheiro(r): ");
                    String opc = sc.nextLine();

                    if(opc.equals("a")){

                        System.out.print("\nDigite o nome do novo cozinheiro: ");
                        String nome = sc.nextLine();

                        System.out.print("\nDigite o CPF do novo cozinheiro: ");
                        Long cpf = sc.nextLong();

                        System.out.print("\nDigite o RG do novo cozinheiro: ");
                        Long rg = sc.nextLong();
                        sc.nextLine();

                        System.out.print("\nDigite o Estado Civil do novo cozinheiro: ");
                        String estadoCivil = sc.nextLine();

                        System.out.print("\nDigite o Endereço do novo cozinheiro: ");
                        String endereco = sc.nextLine();

                        System.out.print("\nDeseja considerar hoje como a Data de Adimissão do novo cozinheiro? (s/n)");
                        String inputOp = sc.nextLine();
                        LocalDate dataAdmissao = null;
                        if (inputOp.charAt(0) == 's' || inputOp.charAt(0) == 'S') {
                            dataAdmissao = Restaurante.dataCentral.toLocalDate();
                        } else {
                            boolean repetExcessoes = false;
                            do {
                                try {
                                    System.out.print("\nDigite a Data de Adimissão (ddmmaa): ");
                                    System.out.print("Ano: ");
                                    int ano = sc.nextInt();
                                    System.out.print("Mes: ");
                                    int mes = sc.nextInt();
                                    System.out.print("Dia: ");
                                    int dia = sc.nextInt();
                                    sc.nextLine();
                                    dataAdmissao = LocalDate.of(ano, mes, dia);
                                } catch (DateTimeException d) {
                                    System.out.println("Você digitou uma data inválida, por favor digite novamente");
                                    repetExcessoes = true;
                                }
                            }while(repetExcessoes);
                        }

                        System.out.print("\nDigite o Número da Carteira do novo cozinheiro: ");
                        long numCarteira = sc.nextLong();

                        System.out.print("\nO novo cozinheiro é especializado em Pratos Principais(1) ou Sobremesas(2)?\n ");
                        boolean pratoEspecializado = sc.nextInt() != 2;

                        Cozinheiro c = null;
                        boolean repetExcessoes = false;
                        do{
                            try {
                                c = new Cozinheiro(nome, cpf, rg, estadoCivil, endereco, dataAdmissao, numCarteira, pratoEspecializado);
                            } catch (CPFInvalido f) {
                                System.out.print("Você digitou um cpf errado, por favor digite novamente: ");
                                cpf = sc.nextLong();
                                repetExcessoes = true;
                            }
                        }while(repetExcessoes);

                        c.calculaSalario();
                        Restaurante.cozinheiros.add(c);

                    }else if(opc.equals("r")){

                        System.out.println("\nPressione 'Enter' para não remover o cozinheiro e 'x' para remover: ");

                        for (Garcom g : Restaurante.garcons) {

                            g.mostrar();
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                Restaurante.garcons.remove(g);
                            }

                        }

                    }

                }
                case 6 -> {

                    System.out.print("\nDeseja cadastrar um novo ingrediente(a) ou remover um ingrediente(r): ");
                    String opc = sc.nextLine();

                    if(opc.equals("a")){

                        System.out.print("\nDigite o Nome do novo ingrediente: ");
                        String nome = sc.nextLine();

                        System.out.print("\nDigite quantos ingredientes devem ser cadastrados: ");
                        int qtd = sc.nextInt();

                        System.out.print("\nDigite o Preço de Custo do novo ingrediente: ");
                        double precoCusto = sc.nextDouble();

                        Ingrediente i = new Ingrediente(nome, qtd, precoCusto);
                        Restaurante.estoque.add(i);

                    }else if(opc.equals("r")){

                        System.out.println("\nPressione 'Enter' para não remover o ingrediente e 'x' para remover: ");

                        for (Ingrediente i : Restaurante.estoque) {

                            i.mostrar();
                            String inputOp = sc.nextLine();
                            if (inputOp.equals("x")) {
                                Restaurante.estoque.remove(i);
                            }

                        }

                    }

                }
                default -> {

                    repeteLoopMenu = false;
                }

            }
        }while(repeteLoopMenu);
    }

    public static void conferir() {
        boolean repeteLoopMenu = true;
        do {

            int op;
            Scanner sc = new Scanner(System.in);

            System.out.println("1 - Olhar estoque");
            System.out.println("2 - Olhar cardápio");
            System.out.println("3 - Ver histórico mensal de pedidos");
            System.out.println("4 - Calcular e mostrar salários");
            System.out.println("Digite qualquer outra tecla para sair.");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> {

                    for (Ingrediente in : Restaurante.estoque) {
                        System.out.print("\n" + in.getNome());
                    }
                    System.out.println();

                }
                case 2 -> {

                    Restaurante.menu();

                }
                case 3 -> {

                    for (Pedido pn : Restaurante.pedidosMensais){
                        pn.mostrar();
                    }

                }
                case 4 -> {

                    Restaurante.mostrarCozinheiros();
                    Restaurante.mostrarGarcons();

                }
                default -> {
                    repeteLoopMenu = false;
                }
            }
        }while(repeteLoopMenu);
    }

    private static void saldoInsuficiente(SaldoInsuficiente f){
        Scanner sc = new Scanner(System.in);
        System.out.println(f);

        int op;
        do{
            System.out.println("1 -> Operar no vermelho");
            System.out.println("2 -> Adicionar mais caixa ao restaurante");
            System.out.println("3 -> Deixar de pagar os seus funcionários");
            op = sc.nextInt();
        }while(op!=1 && op!=2 && op!=3);
        if(op == 1){
            Divida d = new Divida("Banco",f.getValor());
            Restaurante.dividas.add(d);
            System.out.println("Uma nova divida foi criada, consulte pagar divida para paga-la");
        }
        else if(op == 2){
            double quantidadeAdicionada;
            do{
                System.out.println("Digite a quantida que deseja adicionar ao restaurante");
                quantidadeAdicionada = sc.nextDouble();
                if(f.getValor() > quantidadeAdicionada)
                    System.out.println("A quantidade digitada é menor do que o valor da dívida");
            }while(f.getValor() > quantidadeAdicionada);
            Restaurante.adicionarCaixa(quantidadeAdicionada);
            System.out.println("Quantidade adicionada ao caixa com sucesso!");
        }
        else{
            String tipo;
            if(f.getF() == 'c')
                tipo = "Cozinheiros";
            else
                tipo = "Garçons";
            Divida d = new Divida(tipo,f.getValor());
            Restaurante.dividas.add(d);
            System.out.println("Uma nova divida foi criada, consulte pagar divida para paga-la");
        }
    }

    private static void pedidosMensais(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Você deseja ver os pedidos que houveram este mês? (s/n)");
        String in = sc.nextLine();
        if(in.charAt(0)== 's'|| in.charAt(0)== 'S'){
            for(Pedido o: Restaurante.pedidosMensais){
                o.mostrar();
            }
        }
        Restaurante.pedidosMensais.clear();
        System.out.println("Pedidos mensais limpos");
    }

    private static void pagarDividas(){
        System.out.println("Digite a dívida que você deseja pagar");
        System.out.println("Seu caixa atual é de : " + Restaurante.caixa + "R$");
        int op,i;
        Scanner sc = new Scanner(System.in);
        do{
            for(i=0;i<Restaurante.dividas.size();i++){
                System.out.print(i+1 + "  ");
                Restaurante.dividas.get(i).mostarDivida();
            }
            System.out.println(i+2 + " Sair");
            op = sc.nextInt();
        }while(op<0 || op>i+2);
        --op;
        if(op == i+1){
            return;
        }else{
            if(Restaurante.dividas.get(i).getValor() > Restaurante.caixa){
                System.out.println("Saldo Insuficiente");
                return;
            }
            Restaurante.removerCaixa(Restaurante.dividas.get(i).getValor());
            Restaurante.dividas.remove(i);
            System.out.println("Divida paga");
        }

    }

    private static void operacoesDeCaixa(){

        int op;
        double qtdade;
        Scanner sc = new Scanner(System.in);
        System.out.println("O que voce deseja fazer?");
        do{
            System.out.println("1- Adicionar um certo valor ao caixa");
            System.out.println("2- Remover um certo valor do caixa");
            op = sc.nextInt();
        }while(op!=1 &&op!=2);
        if(op == 1){
            System.out.println("Digite a quantidade que voce deseja adicionar");
            qtdade = sc.nextDouble();
        }else{
            System.out.println("Digite a quantidade que voce deseja remover");
            qtdade = sc.nextDouble();
        }
        System.out.println("Quantidade registrada");

    }

    private static void balnçoMensal(){
        if(Restaurante.balancoMensal){
            for (Garcom g : Restaurante.garcons) {
                g.calculaSalario();
            }

            for (Cozinheiro c : Restaurante.cozinheiros) {
                c.calculaSalario();
                c.setNumPratos(0);
            }
            pedidosMensais();
            try{
                Restaurante.pagarCozinheiros();
                Restaurante.pagarGarcons();
            }catch (SaldoInsuficiente f){
                saldoInsuficiente(f);
            }
            Restaurante.balancoMensal = false;
        }else{
            System.out.println("Você não possui nenhum balanço mensal disponível");
        }
    }


}