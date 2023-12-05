package Sistema;
import Enums.FormaPagamentoEnum;
import Itens.*;
import Funcionarios.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Pedido {
    private ArrayList<Item> itensPedido = new ArrayList<Item>();
    private Cozinheiro cozinheiro;
    private Garcom garcom;
    private double valorTotal = 0;
    private LocalDateTime dataHoraRegistro;
    private LocalDateTime dataHoraPagamento;
    private FormaPagamentoEnum pagamento;

    public Pedido(Item []itensPedido, Cozinheiro cozinheiro, Garcom garcom,  String data){
        this.itensPedido.addAll(Arrays.asList(itensPedido));

        for(Item i : itensPedido){
            i.venda();
        }

        this.cozinheiro = cozinheiro;
        this.garcom = garcom;
        this.dataHoraRegistro = Restaurante.dataCentral;

        garcom.pedidoFeito();
        cozinheiro.pedidoFeito();

        Restaurante.pedidosMensais.add(this);
        System.out.println("Pedido registrado com sucesso!");
    }

    public Pedido(Cozinheiro cozinheiro, Garcom garcom, String data){
        this.cozinheiro = cozinheiro;
        this.garcom = garcom;
        this.dataHoraRegistro = Restaurante.dataCentral;
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
        System.out.println("Data e Hora de Registro: " + dataHoraRegistro.toString());
        System.out.println("Data e Hora e Forma do pagamento: " + dataHoraPagamento.toString() + pagamento);

    }

    public void calculaTotal() {
        for (Item i : itensPedido) {
            valorTotal += i.getPrecoUnitario();
        }
    }

    public void pagarConta(FormaPagamentoEnum pagamento){
        this.dataHoraPagamento = Restaurante.dataCentral;
        this.pagamento = pagamento;
    }
}
