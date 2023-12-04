package Sistema;
import java.io.*;
import java.rmi.server.RemoteStub;

import Itens.*;
import Funcionarios.*;

public class Persistencia {
    private static String pathBase = new File("").getAbsolutePath().concat("/src");
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
            arqInBebidas = new FileInputStream(pathBase +"/Arquivos/Bebidas.txt");
            arqInCozinheiros = new FileInputStream(pathBase +"/Arquivos/Cozinheiros.txt");
            arqInEstoque = new FileInputStream(pathBase +"/Arquivos/Estoque.txt");
            arqInGarcons = new FileInputStream(pathBase +"/Arquivos/Garcons.txt");
            arqInPratosPrincipais = new FileInputStream(pathBase +"/Arquivos/Pratos_Principais.txt");
            arqInSobremesas = new FileInputStream(pathBase +"/Arquivos/Sobremesas.txt");
        }catch(IOException f){
            System.out.println("Problema na abertura dos arquivos: " + f);
        }
    }

    public void gravaBase() {
        try{
            arqOutBebidas = new FileOutputStream(pathBase + "/Arquivos/Bebidas.txt");
            arqOutCozinheiros = new FileOutputStream(pathBase +"/Arquivos/Cozinheiros.txt");
            arqOutEstoque = new FileOutputStream(pathBase +"/Arquivos/Estoque.txt");
            arqOutGarcons = new FileOutputStream(pathBase +"/Arquivos/Garcons.txt");
            arqOutPratosPrincipais = new FileOutputStream(pathBase +"/Arquivos/Pratos_Principais.txt");
            arqOutSobremesas = new FileOutputStream(pathBase +"/Arquivos/Sobremesas.txt");
        }catch (IOException f){
            System.out.println("Problema na abertura dos arquivos da gravação" + f);
        }

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
        ObjectInputStream is;
        try{
            is = new ObjectInputStream(arqInBebidas);
            Bebida x = (Bebida) is.readObject();
            while(x != null){
                try{
                    Restaurante.bebidas.add(x);
                    x = (Bebida) is.readObject();
                }catch (EOFException f){
                    is.close();
                    break;
                }
            }
        }catch(Exception f) {
            System.out.println("Problema na leitura das Bebidas: " + f);
        }
        try{
            is = new ObjectInputStream(arqInCozinheiros);
            Cozinheiro x = (Cozinheiro) is.readObject();
            while(x != null){
                try{
                    //System.out.println("Nome do cozinheiro" + x.getNome());
                    Restaurante.cozinheiros.add(x);
                    x = (Cozinheiro) is.readObject();
                }catch (EOFException f){
                    is.close();
                    break;
                }
            }
        }catch(Exception f) {
            System.out.println("Problema na leitura dos cozinheiros: " + f);
        }
        try{
            is = new ObjectInputStream(arqInEstoque);
            Ingrediente x = (Ingrediente) is.readObject();
            while(x != null){
                try{
                    Restaurante.estoque.add(x);
                    x = (Ingrediente) is.readObject();
                }catch (EOFException f) {
                    is.close();
                    break;
                }
            }
        }catch(Exception f) {
            System.out.println("Problema na leitura do estoque: " + f);
        }
        try{
            is = new ObjectInputStream(arqInGarcons);
            Garcom x = (Garcom) is.readObject();
            while(x != null){
                try{
                    //System.out.println("Nome do Graçon: "+ x.getNome());
                    Restaurante.garcons.add(x);
                    x = (Garcom) is.readObject();
                }catch (EOFException f) {
                    is.close();
                    break;
                }
            }
        }catch(Exception f) {
            System.out.println("Problema na leitura dos gracons" + f);
        }
        try{
            is = new ObjectInputStream(arqInPratosPrincipais);
            PratoPrincipal x = (PratoPrincipal) is.readObject();
            while(x != null){
                try{
                    Restaurante.pratosPrincipais.add(x);
                    x = (PratoPrincipal) is.readObject();
                }
               catch (EOFException f){
                   is.close();
                   break;
               }
            }
        }catch(Exception f) {
            System.out.println("Problema na leitura do pratos principais" + f);
        }
        try{
            is = new ObjectInputStream(arqInSobremesas);
            Sobremesa x = (Sobremesa) is.readObject();
            while(x != null){
                try{
                    Restaurante.sobremesas.add(x);
                    x = (Sobremesa) is.readObject();
                }catch (EOFException f){
                    is.close();
                    break;
                }
            }
        }catch(Exception f) {
            System.out.println("Problema na leitura das sobremesas: " + f);
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