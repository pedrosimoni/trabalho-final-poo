package Funcionarios;

import Excecoes.CPFInvalido;

import java.time.LocalDate;

public class Cozinheiro extends Funcionario{
    private boolean pratoEspecializado; //True = Principal || False = Sobremesa
    private int numPratos = 0;
    private static double bonificacaoPrincipal = 50.35;
    private static double bonificacaoSobremesa = 23.15;

    public Cozinheiro(String nome, long cpf, long rg, String estadoCivil, String endereco, LocalDate dataAdmissao, long numCarteira, boolean pratoEspecializado) throws CPFInvalido {
        super(nome, cpf, rg, estadoCivil, endereco, dataAdmissao, numCarteira);
        this.pratoEspecializado = pratoEspecializado;
    }

   public double calculaSalario(){
        if(pratoEspecializado){
            salario = numPratos*bonificacaoPrincipal;
        }else{
            salario = numPratos*bonificacaoSobremesa;
        }

        return salario;
   }

   public void pedidoFeito(){
        numPratos++;
   }

   public void mostrar() {
       System.out.println("Nome: " + nome);
       System.out.println("    Cargo: Cozinheiro");

       if(pratoEspecializado){
           System.out.println("    Prato escpecializado: Prato Principal");
       }else{
           System.out.println("    Prato especializado: Sobremesa");
       }

       calculaSalario();
       System.out.println("    Salário: R$" + salario);
   }

    public boolean isPratoEspecializado() {
        return pratoEspecializado;
    }

    public void setPratoEspecializado(boolean pratoEspecializado) {
        this.pratoEspecializado = pratoEspecializado;
    }

    public int getNumPratos() {
        return numPratos;
    }

    public void setNumPratos(int numPratos) {
        this.numPratos = numPratos;
    }

    public static double getBonificacaoPrincipal() {
        return bonificacaoPrincipal;
    }

    public static void setBonificacaoPrincipal(double bonificacaoPrincipal) {
        Cozinheiro.bonificacaoPrincipal = bonificacaoPrincipal;
    }

    public static double getBonificacaoSobremesa() {
        return bonificacaoSobremesa;
    }

    public static void setBonificacaoSobremesa(double bonificacaoSobremesa) {
        Cozinheiro.bonificacaoSobremesa = bonificacaoSobremesa;
    }
}

