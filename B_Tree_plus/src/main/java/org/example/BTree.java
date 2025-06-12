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
            valorCalc = (int) (Math.round(No.n / 2.0) - 1);

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
        int pos;
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

    public void excluir(int info) {
        No folha = localizarNo(raiz, info);
        if (folha != null) {
            int pos = folha.procurarPosicao(info);
            if (pos < folha.getTl() && folha.getvInfo(pos) == info) {
                for (int i = pos; i < folha.getTl() - 1; i++) {
                    folha.setvInfo(i, folha.getvInfo(i + 1));
                }
                folha.setTl(folha.getTl() - 1);

                if (folha != raiz && folha.getTl() < Math.round((No.n - 1) / 2.0)) {
                    No pai = localizarPai(raiz, folha.getvInfo(0));
                    redistribuiOuConcatena(folha, pai);
                }

                atualizarChavePai(raiz, folha);
            }
        } else
            System.out.println("Info: " + info);

    }

    private No localizarNo(No no, int info) {
        while (no != null && no.getvLig(0) != null) {
            int pos = no.procurarPosicao(info);
            no = no.getvLig(pos);
        }

        if (no != null) {
            for (int i = 0; i < no.getTl(); i++) {
                if (no.getvInfo(i) == info)
                    return no;
            }
        }
        return null;
    }

    private void redistribuiOuConcatena(No folha, No pai) {
        int posPai = 0;

        while (posPai <= pai.getTl() && pai.getvLig(posPai) != folha)
            posPai++;

        No irmaoEsq = (posPai > 0) ? pai.getvLig(posPai - 1) : null;
        No irmaoDir = (posPai < pai.getTl()) ? pai.getvLig(posPai + 1) : null;

        // Redistribuição com irmão esquerdo
        if (irmaoEsq != null && irmaoEsq.getTl() > Math.ceil((No.n - 1) / 2.0)) {
            // Desloca para a direita na folha
            for (int i = folha.getTl(); i > 0; i--) {
                folha.setvInfo(i, folha.getvInfo(i - 1));
            }
            folha.setvInfo(0, irmaoEsq.getvInfo(irmaoEsq.getTl() - 1));
            folha.setTl(folha.getTl() + 1);
            irmaoEsq.setTl(irmaoEsq.getTl() - 1);
            pai.setvInfo(posPai - 1, folha.getvInfo(0));
        }

        // Redistribuição com irmão direito
        else if (irmaoDir != null && irmaoDir.getTl() > Math.round((No.n - 1) / 2.0)) {
            folha.setvInfo(folha.getTl(), irmaoDir.getvInfo(0));
            folha.setTl(folha.getTl() + 1);

            // Desloca para a esquerda no irmão direito
            for (int i = 0; i < irmaoDir.getTl() - 1; i++) {
                irmaoDir.setvInfo(i, irmaoDir.getvInfo(i + 1));
            }
            irmaoDir.setTl(irmaoDir.getTl() - 1);

            // Atualiza chave no pai (ATENÇÃO — aqui estava errado antes!)
            pai.setvInfo(posPai, irmaoDir.getvInfo(0));
        }

        // Concatenação
        else if (irmaoEsq != null) {
            // Concatena folha na esquerda
            for (int i = 0; i < folha.getTl(); i++) {
                irmaoEsq.setvInfo(irmaoEsq.getTl() + i, folha.getvInfo(i));
            }
            irmaoEsq.setTl(irmaoEsq.getTl() + folha.getTl());
            irmaoEsq.setProx(folha.getProx());
            if (folha.getProx() != null)
                folha.getProx().setAnt(irmaoEsq);

            // Remove ponteiro do pai
            for (int i = posPai - 1; i < pai.getTl() - 1; i++) {
                pai.setvInfo(i, pai.getvInfo(i + 1));
                pai.setvLig(i + 1, pai.getvLig(i + 2));
            }
            pai.setTl(pai.getTl() - 1);

            if (pai == raiz && pai.getTl() == 0)
                raiz = irmaoEsq;
        } else if (irmaoDir != null) {
            for (int i = 0; i < irmaoDir.getTl(); i++) {
                folha.setvInfo(folha.getTl() + i, irmaoDir.getvInfo(i));
            }
            folha.setTl(folha.getTl() + irmaoDir.getTl());
            folha.setProx(irmaoDir.getProx());
            if (irmaoDir.getProx() != null)
                irmaoDir.getProx().setAnt(folha);

            // Remove ponteiro e chave do pai
            for (int i = posPai; i < pai.getTl() - 1; i++) {
                pai.setvInfo(i, pai.getvInfo(i + 1));
                pai.setvLig(i + 1, pai.getvLig(i + 2));
            }
            pai.setTl(pai.getTl() - 1);

            // Se o pai virou raiz vazia
            if (pai == raiz && pai.getTl() == 0)
                raiz = folha;
        }
    }

    private void atualizarChavePai(No no, No folha) {
        boolean flag = true;
        for (int i = 0; i <= no.getTl() && flag; i++) {
            if (no.getvLig(i) == folha) {
                if (i > 0) {
                    no.setvInfo(i - 1, folha.getvInfo(0));
                }
                flag = false;
            } else {
                atualizarChavePai(no.getvLig(i), folha);
            }
        }
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
