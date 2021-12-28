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
public class Simbolo{

    int escopo;
    String tipo;
    String valor;
    String lexema;
    String token;
    int linha;
    String oper;

    public Simbolo(){}
    public Simbolo(int escopo, String tipo, String valor) {
        this.escopo = escopo;
        this.tipo = tipo;
        this.valor = valor;
    }
    
}
