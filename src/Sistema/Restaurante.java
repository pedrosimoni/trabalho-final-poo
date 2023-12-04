package Sistema;

import Funcionarios.*;
import Itens.*;
import Enums.*;

import java.util.ArrayList;

public abstract class Restaurante {
        public static ArrayList<Garcom> garcons = new ArrayList<Garcom>();
        public static ArrayList<Cozinheiro> cozinheiros = new ArrayList<Cozinheiro>();
        public static ArrayList<PratoPrincipal> pratosPrincipais = new ArrayList<PratoPrincipal>();
        public static ArrayList<Sobremesa> sobremesas = new ArrayList<Sobremesa>();
        public static ArrayList<Bebida> bebidas = new ArrayList<Bebida>();
        public static ArrayList<Ingrediente> estoque = new ArrayList<Ingrediente>();
        public static ArrayList<Pedido> pedidosMensais = new ArrayList<Pedido>();
        private static double caixa;
        public static DiasEnum diaSemana = DiasEnum.SEGUNDA;
        public static int diaMes = 1;
        public static double dia = 01012023;

        public static void mostrarGarcons(){
            System.out.println("Garçons trabalhando hoje: ");
            for(Garcom g: garcons){
                if(g.getDiaFolga() != diaSemana){
                    g.mostrarNome();
                }
            }
        }

        public static void mostrarCozinheiros(){
            System.out.println("Cozinheiros trabalhando hoje: ");
            for(Cozinheiro c: cozinheiros){
               c.mostrarNome();
            }
        }

        public static void mostrarPratos(){
            System.out.println("Pratos do dia: ");
            for(PratoPrincipal p: pratosPrincipais){
               p.mostrarBasico();
            }
        }

        public static void adicionarCaixa(double venda){
            caixa += venda;
        }
}