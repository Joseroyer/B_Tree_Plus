package org.example;

public class BTree {
    private No raiz;

    public BTree() {
        raiz = null;
    }

    public void inserir(int info) {
        No folha, pai;
        int pos;
        if (raiz == null) {
            raiz = new No(info);
        } else {
            folha = navegarAteFolha(info);
            if (folha != null) {
                pos = folha.procurarPosicao(info);
                folha.remanejar(pos);

                folha.setvInfo(pos, info);
                folha.setTl(folha.getTl() + 1);

                if (folha.getTl() > No.n - 1)// verifica se está cheio
                {
                    pai = localizarPai(folha, info);
                    split(folha, pai);
                }
            } else
                System.out.println("Problema nessa inserção" + info);

        }
    }

    public void split(No folha, No pai) {
        No cx1 = new No();
        No cx2 = new No();
        int pos;
        boolean isFolha = folha.getvLig(0) == null;
        int valorCalc = 0;

        if (isFolha) {
            valorCalc = (int) Math.round((No.n - 1) / 2.0);

            for (int i = 0; i < valorCalc; i++) {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setvLig(i, folha.getvLig(i));
                cx1.setTl(cx1.getTl() + 1);
            }
            cx1.setvLig(valorCalc, folha.getvLig(valorCalc));

            for (int i = valorCalc; i < No.n; i++) {
                cx2.setvInfo(i - valorCalc, folha.getvInfo(i));
                cx2.setvLig(i - valorCalc, folha.getvLig(i));
                cx2.setTl(cx2.getTl() + 1);
            }
            cx2.setvLig(No.n - valorCalc, folha.getvLig(No.n));


            //Ligar as folhas com Cx1 e Cx2
            cx1.setProx(cx2);
            cx2.setAnt(cx1);

            if (folha.getAnt() != null)
                folha.getAnt().setProx(cx1);
            if (folha.getProx() != null)
                folha.getProx().setAnt(cx2);

            cx1.setAnt(folha.getAnt());
            cx2.setProx(folha.getProx());
        } else {
            valorCalc = (int) (Math.round (No.n / 2.0) - 1);

            for (int i = 0; i < valorCalc; i++) {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setvLig(i, folha.getvLig(i));
                cx1.setTl(cx1.getTl() + 1);
            }
            cx1.setvLig(valorCalc, folha.getvLig(valorCalc));

            for (int i = valorCalc + 1; i < No.n; i++) {
                cx2.setvInfo(i - (valorCalc + 1), folha.getvInfo(i));
                cx2.setvLig(i - (valorCalc + 1), folha.getvLig(i));
                cx2.setTl(cx2.getTl() + 1);
            }
            cx2.setvLig(No.n - valorCalc - 1, folha.getvLig(No.n));
        }

        if (folha == pai) {
            folha.setvInfo(0, folha.getvInfo(valorCalc));
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
            folha.setTl(1);
        } else {
            pos = pai.procurarPosicao(folha.getvInfo(valorCalc));
            pai.remanejar(pos);

            //insercao da Info
            pai.setvInfo(pos, folha.getvInfo(valorCalc));

            //apontamento para novas caixas
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
        int pos ;
        while (folha.getvLig(0) != null) {
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

    public void exibirFolha() {
        System.out.println("## Exibir Folhas ##");

        No folha = raiz;
        while (folha.getvLig(0) != null) //Desce até a folha
            folha = folha.getvLig(0);

        while (folha != null) {

            for (int i = 0; i < folha.getTl(); i++) {
                System.out.print(folha.getvInfo(i) + " ");
            }
            System.out.print("\t");
            folha = folha.getProx();
        }
    }

    public void in_ordem() {
        System.out.println("## Exibir In-Ordem##");
        in_ordem(raiz);
    }

    private void in_ordem(No raiz) {
        if (raiz != null) {
            for (int i = 0; i < raiz.getTl(); i++) {
                in_ordem(raiz.getvLig(i));
                System.out.print("\t" + raiz.getvInfo(i));
            }
            in_ordem(raiz.getvLig(raiz.getTl()));
        }
    }
}
