package org.example;
import java.util.Random;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BTree bt = new BTree();
        Random rand = new Random();

        //exemplo de Aula
        bt.inserir(1, 1);
        bt.inserir(4, 4);
        bt.inserir(7, 7);
        bt.inserir(10, 10);
        bt.inserir(17, 17);
        bt.inserir(21, 21);
        bt.inserir(31, 31);
        bt.inserir(25, 25);
        bt.inserir(19, 19);
        bt.inserir(20, 20);
        bt.inserir(28, 28);
        bt.inserir(42, 42);


        for (int i=0; i<40; i++){
//            int valor = rand.nextInt(100); // valores de 0 a 99
            bt.inserir(i,i);
        }
        bt.inserir(40,40);

        bt.exibirFolha();
        bt.in_ordem();
    }
}