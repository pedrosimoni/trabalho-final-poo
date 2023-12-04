import Sistema.*;
import Funcionarios.*;
import Itens.*;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        //Persistencia p = new Persistencia();
        //p.leBase();

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            public void run() {
                System.out.println("Hoje é dia " + Restaurante.diaMes + ", " + Restaurante.diaSemana.toString());
                if (Restaurante.diaMes == 30) {
                    System.out.println("Fim de mês!!");
                    Restaurante.diaMes = 1;

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
                Restaurante.diaSemana = Restaurante.diaSemana.next();
                Restaurante.diaMes++;
            }
        };
        t.scheduleAtFixedRate(tt, 0, 6000);

        /*
        TimerTask tn = new TimerTask(){
            public void run(){
                System.out.println("Eba novo pedido!");
            }
        };
        t.scheduleAtFixedRate(tn, 1000, 1000);
        */
        int op, tamanho, qtd;
        Scanner sc = new Scanner(System.in);
        String nome, codigo, descricao, tempoPreparo, estadoCivil, endereco, diaFolga, tipoEmbalagem;
        double precoUnitario, salarioBase, calorias, dataAdimissão, precoCusto;
        ArrayList<Ingrediente> lista = new ArrayList<Ingrediente>();
        long cpf, rg, numCarteira;
        boolean pratoEspecializado;

        do {
            System.out.println("Bem vindo ao sistema!");
            System.out.println("1 - Cadastrar novo prato principal");
            System.out.println("2 - Cadastrar nova sobremesa");
            System.out.println("3 - Cadastrar novo garçom");
            System.out.println("4 - Cadastrar novo cozinheiro");
            System.out.println("5 - Cadastrar nova bebida");
            System.out.println("6 - Cadastrar novo ingrediente");
            System.out.println("7 - Fazer Compras");
            System.out.println("8 - Olhar estoque de ingredientes");
            System.out.println("9 - Olhar cardápio");
            System.out.println("10 - Ver histórico de pedidos do mês");
            System.out.println("11 - Calcular e mostrar salários dos funcionários");
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();

            switch (op){
                case 1:

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
                    System.out.println("\nEscolha os ingredientes do prato: ");
                    for(Ingrediente i : Restaurante.estoque){
                       System.out.printf(i.getNome() + "(s/n)");
                       if(sc.next().charAt(0) == 's' || sc.next().charAt(0) == 'S'){
                           lista.add(i);
                       }
                    }

                    PratoPrincipal p = new PratoPrincipal(nome, codigo, precoUnitario, lista, descricao, tempoPreparo);
                    p.calculaPrecoCusto();
                    lista.clear();
                    Restaurante.pratosPrincipais.add(p);

                    break;

                case 2:

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
                    System.out.println("\nEscolha os ingredientes da sobremesa: ");
                    for(Ingrediente i : Restaurante.estoque){
                        System.out.printf(i.getNome() + "(s/n)");
                        if(sc.next().charAt(0) == 's' || sc.next().charAt(0) == 'S'){
                            lista.add(i);
                        }
                    }
                    System.out.print("\nDigite a quantidade de calorias na sobremesa: ");
                    calorias = sc.nextFloat();

                    Sobremesa s = new Sobremesa(nome, codigo, precoUnitario, lista, descricao, tempoPreparo, calorias);
                    s.calculaPrecoCusto();
                    lista.clear();
                    Restaurante.sobremesas.add(s);

                    break;

                case 3:

                    System.out.print("\nDigite o nome do novo garçom: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o CPF do novo garçom: ");
                    cpf = sc.nextLong();
                    System.out.print("\nDigite o RG do novo garçom: ");
                    rg = sc.nextLong();
                    System.out.print("\nDigite o Estado Civil do novo garçom: ");
                    estadoCivil = sc.nextLine();
                    System.out.print("\nDigite o Endereço do novo garçom: ");
                    endereco = sc.nextLine();
                    System.out.print("\nDeseja considerar hoje como a Aata de Adimissão do novo garçom? (s/n)");
                    if(sc.next().charAt(0) == 's' || sc.next().charAt(0) == 'S') {
                        dataAdimissão = Restaurante.dia;
                    }else {
                        System.out.print("\nDigite a Data de Adimissão: ");
                        dataAdimissão = sc.nextFloat();
                    }
                    System.out.print("\nDigite o Número da Carteira do novo garçom: ");
                    numCarteira = sc.nextLong();
                    System.out.print("\nDigite o dia de folga do novo garçom: ");
                    diaFolga = sc.nextLine();
                    System.out.print("Digite o Salário Base do novo garçom: ");
                    salarioBase = sc.nextFloat();

                    Garcom g = new Garcom(nome, cpf, rg, estadoCivil, endereco, dataAdimissão, numCarteira, diaFolga, salarioBase);
                    g.calculaSalario();
                    Restaurante.garcons.add(g);

                    break;

                case 4:

                    System.out.print("\nDigite o nome do novo cozinheiro: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite o CPF do novo cozinheiro: ");
                    cpf = sc.nextLong();
                    System.out.print("\nDigite o RG do novo cozinheiro: ");
                    rg = sc.nextLong();
                    System.out.print("\nDigite o Estado Civil do novo cozinheiro: ");
                    estadoCivil = sc.nextLine();
                    System.out.print("\nDigite o Endereço do novo cozinheiro: ");
                    endereco = sc.nextLine();
                    System.out.print("\nDeseja considerar hoje como a Aata de Adimissão do novo cozinheiro? (s/n)");
                    if(sc.next().charAt(0) == 's' || sc.next().charAt(0) == 'S') {
                        dataAdimissão = Restaurante.dia;
                    }else {
                        System.out.print("\nDigite a Data de Adimissão (ddmmaa): ");
                        dataAdimissão = sc.nextFloat();
                    }
                    System.out.print("\nDigite o Número da Carteira do novo cozinheiro: ");
                    numCarteira = sc.nextLong();
                    System.out.print("\nO novo cozinheiro é especializado em Pratos Principais(1) ou Sobremesas(2)?\n ");
                    pratoEspecializado = sc.nextInt() != 2;

                    Cozinheiro c = new Cozinheiro(nome, cpf, rg, estadoCivil, endereco, dataAdimissão, numCarteira, pratoEspecializado);
                    c.calculaSalario();
                    Restaurante.cozinheiros.add(c);

                    break;

                case 5:

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
                    System.out.print("\nLata(1)\nGarrafa Plástica(2)\nGarrafa de Vidro(3)\nRefil(4)");
                    switch (sc.nextInt()) {
                        default -> tipoEmbalagem = "LATA";
                        case 2 -> tipoEmbalagem = "GARRAFA_PLASTICA";
                        case 3 -> tipoEmbalagem = "GARRAFA_VIDRO";
                        case 4 -> tipoEmbalagem = "COPO_REFIL";
                    }

                    Bebida b = new Bebida(nome, codigo, precoUnitario, precoCusto, tamanho, tipoEmbalagem);
                    Restaurante.bebidas.add(b);

                    break;

                case 6:

                    System.out.print("\nDigite o Nome do novo ingrediente: ");
                    nome = sc.nextLine();
                    System.out.print("\nDigite quantos ingredientes devem ser cadastrados: ");
                    qtd = sc.nextInt();
                    System.out.print("\nDigite o Preço de Custo do novo ingrediente: ");
                    precoCusto = sc.nextDouble();

                    Ingrediente i = new Ingrediente(nome, qtd, precoCusto);
                    Restaurante.estoque.add(i);

                    break;

                case 7:
                    System.out.print("\nPressione 'Enter' para não comprar e a quantidade desejada para comprar: \n");
                    for(Ingrediente in : Restaurante.estoque){
                        System.out.print("\n" + in.getNome() + ": ");
                        in.compra(sc.nextInt());
                    }
                    System.out.print("\nObrigado por comprar!");

                    break;

                case 8:

                    for(Ingrediente in : Restaurante.estoque){
                        System.out.print("\n" + in.getNome());
                    }

                    break;

                case 9:

                    System.out.print("\nPratos Principais: ");
                    for(PratoPrincipal pn : Restaurante.pratosPrincipais){
                        pn.mostrarBasico();
                    }
                    System.out.print("\nBebidas: ");
                    for(Bebida bn : Restaurante.bebidas){
                        bn.mostrarBasico();
                    }
                    for(Sobremesa sn : Restaurante.sobremesas){
                        sn.mostrarBasico();
                    }

                    break;

                case 10:

                    for(Pedido pn : Restaurante.pedidosMensais){
                        pn.mostrar();
                    }

                    break;

                case 11:

                    System.out.println("Cozinheiros: ");
                    for(Cozinheiro cn : Restaurante.cozinheiros){
                        System.out.print("    Nome: ");
                        cn.mostrarNome();
                        System.out.println("    Salário: " + cn.calculaSalario());
                    }
                    System.out.println("Garçons: ");
                    for(Garcom gn : Restaurante.garcons){
                        System.out.print("    Nome: ");
                        gn.mostrarNome();
                        System.out.println("    Salário: " + gn.calculaSalario());

                    }


            }
        } while (op != 10);

        //p.gravaBase();
        //p.fecharArquivos();
    }
}
