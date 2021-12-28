/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Tchingunji
 */
public class Erro {
    int linha;
    String espc;

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public String getEspc() {
        return espc;
    }

    public void setEspc(String espc) {
        this.espc = espc;
    }

    public Erro(int linha, String espc) {
        this.linha = linha;
        this.espc = espc;
    }

    public Erro() {
    }
    
}
