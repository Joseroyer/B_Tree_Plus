package org.example;

public class BTree {
    private No raiz;

    public void inserir(int info, int posArq) {
        No folha, pai;
        int pos;
        if (raiz == null) { 
            raiz = new No(info, posArq);
        } else {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);

            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl() + 1);

            if (folha.getTl() > No.n - 1)// verifica se está cheio
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public void split(No folha, No pai) {
        //verifica se split é da folha ou do no
        No cx1 = new No();
        No cx2 = new No();
        int pos;
        boolean isFolha = folha.getvLig(0) == null;
        int valorCalc = 0;

        if (isFolha) {
            valorCalc = (int) Math.ceil((No.n - 1) / 2.0);

            for (int i = 0; i < valorCalc; i++) {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setvPos(i, folha.getvPos(i));
                cx1.setvLig(i, folha.getvLig(i));
                cx1.setTl(cx1.getTl() + 1);
            }
            cx1.setvLig(valorCalc, folha.getvLig(valorCalc));

            for (int i = valorCalc; i < folha.getTl(); i++) {
                cx2.setvInfo(i - valorCalc, folha.getvInfo(i));
                cx2.setvPos(i - valorCalc, folha.getvPos(i));
                cx2.setvLig(i - valorCalc, folha.getvLig(i));
                cx2.setTl(cx2.getTl() + 1);
            }
            cx2.setvLig(No.n - valorCalc, folha.getvLig(No.n));
        } else {
            valorCalc = (int) Math.ceil((No.n / 2.0) - 1);

        }

        //Ligar as folhas
        if (folha == pai) {
            folha.setvInfo(0, folha.getvInfo(valorCalc));
            folha.setvPos(0, folha.getvPos(valorCalc));
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
            folha.setTl(1);
        } else {
            pos = pai.procurarPosicao(folha.getvInfo(valorCalc));
            pai.remanejar(pos);

            //insercao
            pai.setvInfo(pos, folha.getvInfo(valorCalc));
            pai.setvPos(pos, folha.getvPos(valorCalc));

            //apontamentos para nova caixa
            pai.setvLig(pos, cx1);
            pai.setvLig(pos + 1, cx2);
            pai.setTl(pai.getTl() + 1);

            if (pai.getTl() > No.n - 1) {
                folha = pai;
                pai = localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }

    }

    private No navegarAteFolha(int info) {
        No folha = raiz;
        int pos = 0;
        while (folha.getvLig(pos) != null) {
            pos = folha.procurarPosicao(info);
            folha = folha.getvLig(pos);
        }
        return folha;
    }

    private No localizarPai(No folha, int info) {
        No no = raiz, pai = raiz;
        int pos;
        while (no != folha) {
            pai = no;
            pos = no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return pai;
    }
}
