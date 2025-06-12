package org.example;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BTree bt = new BTree();

//        exemplo de Aula
//        bt.inserir(1);
//        bt.inserir(4);
//        bt.inserir(7);
//        bt.inserir(10);
//        bt.inserir(17);
//        bt.inserir(21);
//        bt.inserir(31);
//        bt.inserir(25);
//        bt.inserir(19);
//        bt.inserir(20);
//        bt.inserir(28);
//        bt.inserir(42);

        System.out.println("Inserção feita com sucesso");

        for (int i=0; i<100; i++){
            bt.inserir(i);
        }
        bt.exibirFolha();

        System.out.println("\n");

//        for (int i=6; i<100; i++){
//            bt.excluir(i);
//        }
//        bt.excluir(42);
        System.out.println("Exibir pós Exclusão");
        bt.exibirFolha();
    }
}