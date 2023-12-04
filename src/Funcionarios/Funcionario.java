package Funcionarios;
import Excecoes.*;
import Sistema.*;
import java.io.*;
import java.time.LocalDate;

abstract class Funcionario implements Serializable {
    protected String nome;
    protected long cpf;
    protected long rg;
    protected String estadoCivil;
    protected String endereco;
    protected LocalDate dataAdmissao;
    protected long numCarteira;
    protected double salario;


    public Funcionario(String nome, long cpf, long rg, String estadoCivil, String endereco, LocalDate dataAdmissao, long numCarteira) throws CPFInvalido{
        this.nome = nome;
        ValidadorCPF.valida(cpf);
        this.cpf = cpf;
        this.rg = rg;
        this.estadoCivil = estadoCivil;
        this.endereco = endereco;
        this.dataAdmissao = dataAdmissao;
        this.numCarteira = numCarteira;

        System.out.println("Funcionário " + nome + " cadastrado com sucesso!");
    }

    public Funcionario(String nome, long cpf) throws CPFInvalido{
        this.nome = nome;
        ValidadorCPF.valida(cpf);
        this.cpf = cpf;
        this.rg = 0;
        this.estadoCivil = "Não cadastrado";
        this.endereco = "Não cadastrado";
        this.dataAdmissao = LocalDate.of(0,1,1);
        this.numCarteira = 0;
    }

    public Funcionario(){
        this.nome = "Não cadastrado";
        this.cpf = 0;
        this.rg = 0;
        this.estadoCivil = "Não cadastrado";
        this.endereco = "Não cadastrado";
        this.dataAdmissao = LocalDate.of(0,1,1);
        this.numCarteira = 0;
    }

    public void mostrarNome(){
        System.out.print(nome);
    }

    public double calculaSalario(){
        return salario;
    }

    public String getNome() {
        return nome;
    }
}
