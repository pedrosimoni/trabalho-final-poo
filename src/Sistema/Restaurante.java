package Sistema;

import Funcionarios.*;
import Itens.*;
import Enums.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Restaurante {
        public static ArrayList<Garcom> garcons = new ArrayList<Garcom>();
        public static ArrayList<Cozinheiro> cozinheiros = new ArrayList<Cozinheiro>();
        public static ArrayList<PratoPrincipal> pratosPrincipais = new ArrayList<PratoPrincipal>();
        public static ArrayList<Sobremesa> sobremesas = new ArrayList<Sobremesa>();
        public static ArrayList<Bebida> bebidas = new ArrayList<Bebida>();
        public static ArrayList<Ingrediente> estoque = new ArrayList<Ingrediente>();
        public static ArrayList<Pedido> pedidosMensais = new ArrayList<Pedido>();
        private static double caixa;
        //public static int diaMes = 1;
        //public static double dia = 01012023;
        public static LocalDateTime dataCentral = LocalDateTime.now();
        public static DiasEnum diaSemana = DiasEnum.SEGUNDA;

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
}