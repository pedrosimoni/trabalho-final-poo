package Funcionarios;
import Excecoes.*;
import Sistema.*;
import java.io.*;

abstract class Funcionario implements Serializable {
    protected String nome;
    protected long cpf;
    protected long rg;
    protected String estadoCivil;
    protected String endereco;
    protected double dataAdmissao;
    protected long numCarteira;
    protected double salario;


    public Funcionario(String nome, long cpf, long rg, String estadoCivil, String endereco, double dataAdmissao, long numCarteira){
        this.nome = nome;
        int teste = ValidadorCPF.valida(cpf);
        if(teste == 0){
            this.cpf = 0;
            //throw new CPFInvalido("CPF digitado não existe! Tente novamente.");
        }else{
            this.cpf = cpf;
        }
        this.rg = rg;
        this.estadoCivil = estadoCivil;
        this.endereco = endereco;
        this.dataAdmissao = dataAdmissao;
        this.numCarteira = numCarteira;

        System.out.println("Funcionário " + nome + " cadastrado com sucesso!");
    }

    public Funcionario(String nome, long cpf){
        this.nome = nome;
        int teste = ValidadorCPF.valida(cpf);
        if(teste == 0){
            this.cpf = 0;
            //throw new CPFInvalido("CPF digitado não existe! Tente novamente.");
        }else{
            this.cpf = cpf;
        }
        this.rg = 0;
        this.estadoCivil = "Não cadastrado";
        this.endereco = "Não cadastrado";
        this.dataAdmissao = 0;
        this.numCarteira = 0;
    }

    public Funcionario(){
        this.nome = "Não cadastrado";
        this.cpf = 0;
        this.rg = 0;
        this.estadoCivil = "Não cadastrado";
        this.endereco = "Não cadastrado";
        this.dataAdmissao = 0;
        this.numCarteira = 0;
    }

    public void mostrarNome(){
        System.out.print(nome);
    }

    public double calculaSalario(){
        return salario;
    }
}
