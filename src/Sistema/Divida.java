package Sistema;

public class Divida {
    private String tipo;
    private double valor;

    public Divida(String tipo,double valor){
        this.tipo = tipo;
        this.valor = valor;
    }
    public void mostarDivida(){
        System.out.println("Tipo da dívida: " + tipo);
        System.out.println("Valor da dívida: " + valor);
    }

    public double getValor() {
        return valor;
    }
}
