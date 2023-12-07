package Funcionarios;
import Enums.DiasEnum;
import Excecoes.CPFInvalido;

import java.time.LocalDate;

public class Garcom extends Funcionario {
    private DiasEnum diaFolga;
    private static double salarioBase = 2365.15;
    public static int numPedidos = 0;
    public static int metaPedidos = 3000;
    private static double bonificacao = 500;
    public static boolean bateuMeta;

    public Garcom(String nome, long cpf, long rg, String estadoCivil, String endereco, LocalDate dataAdmissao, long numCarteira, String diaFolga, double salarioBase) throws CPFInvalido {
        super(nome, cpf, rg, estadoCivil, endereco, dataAdmissao, numCarteira);
        this.diaFolga = DiasEnum.valueOf(diaFolga);
    }

    public void pedidoFeito() {
        numPedidos++;
    }

    public double calculaSalario() {
        if (bateuMeta) {
            salario = salarioBase + bonificacao;
        } else {
            salario = salarioBase;
        }

        return salario;
    }

    public void mostrar() {
        System.out.println("Nome: " + nome);
        System.out.println("    Cargo: Garçom");
        System.out.println("    Número da carteira: " + numCarteira);
        calculaSalario();
        System.out.println("    Salário: R$" + salario);
    }

    public DiasEnum getDiaFolga() {
        return diaFolga;
    }

}