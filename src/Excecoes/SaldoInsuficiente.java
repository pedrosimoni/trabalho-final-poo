package Excecoes;

public class SaldoInsuficiente extends Exception{
    private char f;
    private double valor;
    public SaldoInsuficiente(String funcionarios,char f,double valor){
        super("Saldo insuficiente, você não pode pagar os "+ funcionarios+ "o que deseja fazer?");
        this.f = f;
        this.valor = valor;
    }
    public char getF(){
        return f;
    }
    public double getValor(){
        return valor;
    }
}
