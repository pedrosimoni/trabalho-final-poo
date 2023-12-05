package Sistema;
import Enums.FormaPagamentoEnum;
import Itens.*;
import Funcionarios.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Pedido {
    private ArrayList<Item> itensPedido = new ArrayList<Item>();
    private Cozinheiro cozinheiro;
    private Garcom garcom;
    private double valorTotal = 0;
    private String data;
    private String horaRegistro;
    private String horaPagamento;
    private FormaPagamentoEnum pagamento;

    public Pedido(Item []itensPedido, Cozinheiro cozinheiro, Garcom garcom,  String data, String horaRegistro){
        this.itensPedido.addAll(Arrays.asList(itensPedido));

        for(Item i : itensPedido){
            i.venda();
        }

        this.cozinheiro = cozinheiro;
        this.garcom = garcom;
        this.data = data;
        this.horaRegistro = horaRegistro;

        garcom.pedidoFeito();
        cozinheiro.pedidoFeito();

        System.out.println("Pedido registrado com sucesso!");
    }

    public Pedido(Cozinheiro cozinheiro, Garcom garcom, String data, String horaRegistro){
        this.cozinheiro = cozinheiro;
        this.garcom = garcom;
        this.data = data;
        this.horaRegistro = horaRegistro;
    }

    public void adicionaPedido(Item []itensPedidos){
        for(Item i : itensPedidos){
            i.venda();
        }
        this.itensPedido.addAll(Arrays.asList((itensPedidos)));
    }

    public void mostrar(){
        System.out.println("Itens: ");
        for(Item i : itensPedido){
            System.out.println("    " + i.getNome());
        }
        System.out.println("Cozinheiro Responsável: ");
        cozinheiro.mostrar();
        System.out.println("Garçom responsável: ");
        garcom.mostrar();
        System.out.println("Valor total: " + valorTotal);
        System.out.println("Data e Hora de Registro: " + data + horaRegistro);
        System.out.println("Hora e Forma do pagamento: " + horaPagamento + pagamento);

    }

    public void calculaTotal() {
        for (Item i : itensPedido) {
            valorTotal += i.getPrecoUnitario();
        }
    }

    public void pagarConta(String horaPagamento, FormaPagamentoEnum pagamento){
        this.horaPagamento = horaPagamento;
        this.pagamento = pagamento;
    }
}
