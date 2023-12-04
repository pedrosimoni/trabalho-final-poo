package Sistema;
import java.io.*;
import java.rmi.server.RemoteStub;

import Itens.*;
import Funcionarios.*;

public class Persistencia {
    private FileOutputStream arqOutBebidas;
    private FileOutputStream arqOutCozinheiros;
    private FileOutputStream arqOutEstoque;
    private FileOutputStream arqOutGarcons;
    private FileOutputStream arqOutPratosPrincipais;
    private FileOutputStream arqOutSobremesas;

    private FileInputStream arqInBebidas;
    private FileInputStream arqInCozinheiros;
    private FileInputStream arqInEstoque;
    private FileInputStream arqInGarcons;
    private FileInputStream arqInPratosPrincipais;
    private FileInputStream arqInSobremesas;

    public Persistencia(){
        try{
            arqOutBebidas = new FileOutputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Bebidas");
            arqOutCozinheiros = new FileOutputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Cozinheiros");
            arqOutEstoque = new FileOutputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Estoque");
            arqOutGarcons = new FileOutputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Garcons");
            arqOutPratosPrincipais = new FileOutputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Pratos_Principais");
            arqOutSobremesas = new FileOutputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Sobremesas");

            arqInBebidas = new FileInputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Sobremesas");
            arqInCozinheiros = new FileInputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Cozinheiros");
            arqInEstoque = new FileInputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Estoque");
            arqInGarcons = new FileInputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Garcons");
            arqInPratosPrincipais = new FileInputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Pratos_Principais");
            arqInSobremesas = new FileInputStream("C:/Faculdade/POO/Trabalho_Final/src/Arquivos/Sobremesas");
        }catch(IOException f){
            System.out.println("Problema na abertura dos arquivos: " + f);
        }
    }

    public void gravaBase() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(arqOutBebidas);
            for (Bebida o : Restaurante.bebidas) {
                os.writeObject(o);
            }
            os = new ObjectOutputStream(arqOutCozinheiros);
            for (Cozinheiro o : Restaurante.cozinheiros) {
                os.writeObject(o);
            }
            os = new ObjectOutputStream(arqOutEstoque);
            for (Ingrediente o : Restaurante.estoque) {
                os.writeObject(o);
            }
            os = new ObjectOutputStream(arqOutGarcons);
            for (Garcom o : Restaurante.garcons) {
                os.writeObject(o);
            }
            os = new ObjectOutputStream(arqOutPratosPrincipais);
            for (PratoPrincipal o : Restaurante.pratosPrincipais) {
                os.writeObject(o);
            }
            os = new ObjectOutputStream(arqOutSobremesas);
            for (Sobremesa o : Restaurante.sobremesas) {
                os.writeObject(o);
            }

            os.close();
        } catch (IOException f) {
            System.out.println("Problema na gravação da base de dados: " + f);
        }
    }

    public void leBase(){
        try{
            ObjectInputStream is = new ObjectInputStream(arqInBebidas);
            while(is.readObject() != null){
                Bebida x = (Bebida) is.readObject();
                Restaurante.bebidas.add(x);
            }
            is = new ObjectInputStream(arqInCozinheiros);
            while(is.readObject() != null){
                Cozinheiro x = (Cozinheiro) is.readObject();
                Restaurante.cozinheiros.add(x);
            }
            is = new ObjectInputStream(arqInEstoque);
            while(is.readObject() != null){
                Ingrediente x = (Ingrediente) is.readObject();
                Restaurante.estoque.add(x);
            }
            is = new ObjectInputStream(arqInGarcons);
            while(is.readObject() != null){
                Garcom x = (Garcom) is.readObject();
                Restaurante.garcons.add(x);
            }
            is = new ObjectInputStream(arqInPratosPrincipais);
            while(is.readObject() != null){
                PratoPrincipal x = (PratoPrincipal) is.readObject();
                Restaurante.pratosPrincipais.add(x);
            }
            is = new ObjectInputStream(arqInSobremesas);
            while(is.readObject() != null){
                Sobremesa x = (Sobremesa) is.readObject();
                Restaurante.sobremesas.add(x);
            }

            is.close();
        }catch(Exception f) {
            System.out.println("Problema na gravação da base de dados: " + f);
        }
    }

    public void fecharArquivos(){
        try{
           arqOutBebidas.close();
           arqOutCozinheiros.close();
           arqOutEstoque.close();
           arqOutEstoque.close();
           arqOutGarcons.close();
           arqOutPratosPrincipais.close();
           arqOutSobremesas.close();

           arqInBebidas.close();
           arqInCozinheiros.close();
           arqInEstoque.close();
           arqInEstoque.close();
           arqInGarcons.close();
           arqInPratosPrincipais.close();
           arqInSobremesas.close();
        }catch (Exception f){
            System.out.println("Problema ao fechar arquivos: " + f);
        }
    }
}