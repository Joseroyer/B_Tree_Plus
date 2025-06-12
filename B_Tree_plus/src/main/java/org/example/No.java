package org.example;

public class No {
    public static final int n = 4;
    private int[] vInfo;
    private No[] vLig;
    private No ant, prox;
    private int tl;


    public No() {
        this.vInfo = new int[n];
        this.vLig = new No[n + 1];
        this.tl = 0;
        this.ant = null;
        this.prox = null;
    }

    public No(int info) {
        this();
        vInfo[0] = info;
        tl = 1;
    }

    public int procurarPosicao(int info) {
        int pos = 0;
        while (pos < tl && info > vInfo[pos])
            pos++;
        return pos;
    }

    public void remanejar(int pos) {
        vLig[tl + 1] = vLig[tl];
        for (int i = tl; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];
            vLig[i] = vLig[i - 1];
        }
    }

    public int buscarPosicaoExata(int info) {
        for (int i = 0; i < getTl(); i++) {
            if (vInfo[i] == info) return i;
        }
        return -1;
    }

    public void remanejarExclusao(int pos) {
        for (int i = pos; i < tl - 1; i++) {
            vInfo[i] = vInfo[i + 1];
            vLig[i] = vLig[i + 1];
        }
        vLig[tl - 1] = vLig[tl];
    }

    public int getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, int info) {
        vInfo[p] = info;
    }


    public No getvLig(int p) {
        return vLig[p];
    }

    public void setvLig(int p, No lig) {
        vLig[p] = lig;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
