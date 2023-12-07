package Sistema;

import Excecoes.SaldoInsuficiente;
import Funcionarios.*;
import Itens.*;
import Enums.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;

public class Restaurante {
        public static ArrayList<Garcom> garcons = new ArrayList<Garcom>();
        public static ArrayList<Cozinheiro> cozinheiros = new ArrayList<Cozinheiro>();
        public static ArrayList<PratoPrincipal> pratosPrincipais = new ArrayList<PratoPrincipal>();
        public static ArrayList<Sobremesa> sobremesas = new ArrayList<Sobremesa>();
        public static ArrayList<Bebida> bebidas = new ArrayList<Bebida>();
        public static ArrayList<Ingrediente> estoque = new ArrayList<Ingrediente>();
        public static ArrayList<Pedido> pedidosMensais = new ArrayList<Pedido>();
        public static Stack<Pedido> pedidosEsperandoAprovacao = new Stack<Pedido>();
        public static ArrayList<Divida> dividas = new ArrayList<Divida>();
        public static double caixa = 500000;
        public static LocalDateTime dataCentral = LocalDateTime.now();
        public static DiasEnum diaSemana = DiasEnum.SEGUNDA;
        public static boolean balancoMensal;

        public static void mostrarGarcons(){
            System.out.println("Garçons trabalhando hoje: ");
            for(Garcom g: garcons){
                if(g.getDiaFolga() != diaSemana){
                    g.mostrar();
                }
            }
        }

        public static void mostrarCozinheiros(){
            System.out.println("Cozinheiros trabalhando hoje: ");
            for(Cozinheiro c: cozinheiros){
               c.mostrar();
            }
        }

        public static void menu(){
            System.out.print("\nPratos Principais: \n\n");
            for (PratoPrincipal pn : pratosPrincipais) {
                pn.mostrarBasico();
            }
            System.out.print("\nBebidas: \n\n");
            for (Bebida bn : Restaurante.bebidas) {
                bn.mostrarBasico();
            }
            System.out.print("\nSobremesas: \n\n");
            for (Sobremesa sn : Restaurante.sobremesas) {
                sn.mostrarBasico();
            }
        }

        public static void adicionarCaixa(double venda){
            caixa += venda;
        }
        public static void removerCaixa(double venda){
        caixa -= venda;
    }

        public static void  setDiaSemana(){
            DayOfWeek day= dataCentral.getDayOfWeek();
            switch (day){
                case MONDAY -> diaSemana = DiasEnum.SEGUNDA;
                case TUESDAY-> diaSemana = DiasEnum.TERCA;
                case WEDNESDAY -> diaSemana = DiasEnum.QUARTA;
                case THURSDAY -> diaSemana = DiasEnum.QUINTA;
                case FRIDAY -> diaSemana = DiasEnum.SEXTA;
                case SATURDAY -> diaSemana = DiasEnum.SABADO;
                case SUNDAY -> diaSemana = DiasEnum.DOMINGO;
            }
        }

        public static void pagarCozinheiros() throws SaldoInsuficiente {
            double p=0;
            for(Cozinheiro o: cozinheiros){
                p+= o.calculaSalario();
            }
            if(p > caixa){
                throw new SaldoInsuficiente("Cozinheiros",'c',p);
            }
            for(Cozinheiro o: cozinheiros){
                System.out.println("Cozinheiro "+ o.getNome() + " pago");
                removerCaixa(o.calculaSalario());
            }
        }
        public static void pagarGarcons() throws SaldoInsuficiente{
            double p=0;
            for(Garcom o: garcons){
                p+= o.calculaSalario();
            }
            if(p > caixa){
                throw new SaldoInsuficiente("Garçon",'g',p);
            }
            for(Cozinheiro o: cozinheiros){
                System.out.println("Garçon "+ o.getNome() + " pago");
                removerCaixa(o.calculaSalario());
            }
        }
}