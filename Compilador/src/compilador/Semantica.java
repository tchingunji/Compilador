/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tchingunji
 */
public class Semantica {
    static int conta;
    static ArrayList<Simbolo>Tb=Sintatico.TbSimbolo;
    boolean stop=true;
    public static ArrayList<Erro> listaErro;
    public Semantica() {
        conta=0;
        listaErro=Sintatico.listaErro;
        Tb=Sintatico.TbSimbolo;
        VerificaQdtDecla();
        BLOCO(); 
    }    
    
    private void BLOCO(){
        while(conta<Tb.size())
        {
            if(Tb.get(conta).valor!=null)
            {
                String tipo = qualTipoDados(Tb.get(conta).lexema);
                if(tipo!=null)
                {
                    if(tipo.equals("int")||tipo.equals("long")||tipo.equals("byte")){
                        validaAtrInteira(Tb.get(conta).valor);
                    }else if(tipo.equals("char")){
                        validaAtrChar(Tb.get(conta).valor);
                    }else if(tipo.equals("float")||tipo.equals("double")){
                        validaReais(Tb.get(conta).valor);
                    }else if(tipo.equals("boolean")){
                        validaBoolean(Tb.get(conta).valor);
                    }else if(tipo.equals("String")){
                        validaString(Tb.get(conta).valor);
                    }
                }
                else
                {
                    Erro e=new Erro(Tb.get(conta).linha, "Variavel ("+Tb.get(conta).lexema+") não declarada");
                    listaErro.add(e);
                }
                conta++;
            }
            else if(Tb.get(conta).lexema.equals("if")){
                stop=true;
                conta++;
                ExpressaBoolean();
            }
            else if(Tb.get(conta).lexema.equals("while")){
                stop=true;
                conta++;
                ExpressaBoolean();
            }
            else if(Tb.get(conta).lexema.equals("for")){
                conta++;conta++;
                stop=true;
                PriParte();
                ExpressaBoolean2();
            }
            else
            {
                conta++;
            }
        }
    }
    
    private void PriParte()
    {
        while(!Tb.get(conta).lexema.equals(";")&& conta<Tb.size())
        {
            if(conta<Tb.size())
            {
                String tipo=Tb.get(conta).tipo;
                if(tipo!=null)
                {
                    if(tipo.equals("int")){
                        validaAtrInteira(Tb.get(conta).valor);
                    }else if(tipo.equals("float")||tipo.equals("double")){
                        validaReais(Tb.get(conta).valor);
                    }else if(tipo.equals("char")){
                        validaAtrChar(Tb.get(conta).valor);
                    }
                    conta++;
                }
                else if(Tb.get(conta).lexema.equals(","))
                {
                    conta++;
                }
                else if(tipo==null)
                {
                    String id=Tb.get(conta).lexema;
                    tipo = qualTipoDados(id);
                    if(tipo!=null)
                    {
                        if(tipo.equals("int")){
                            validaAtrInteira(Tb.get(conta).valor);
                        }else if(tipo.equals("float")||tipo.equals("double")){
                            validaReais(Tb.get(conta).valor);
                        }else if(tipo.equals("char")){
                            validaAtrChar(Tb.get(conta).valor);
                        }
                    }
                    else
                    {
                        Erro e=new Erro(Tb.get(conta).linha, "Variaval ("+id+") não declarada");
                        listaErro.add(e);
                    }
                    conta++;
                    if(!(conta<Tb.size())){
                        break;
                    }
                }                
            }          
        }
    }
    private void UltParte()
    {
        if(conta<Tb.size()){
            String type=qualTipoDados(Tb.get(conta).lexema);
            if(type!=null){
                if(type.equals("int")){
                    
                }
            }else{
                Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                listaErro.add(e);
            }
        }
    }
    private void VerificaQdtDecla()
    {        
        for (int i = 0; i < Tb.size(); i++) {
            int qtd=0;
            if(Tb.get(i).oper!=null){
                if(Tb.get(i).oper.equals("Decl")){
                    for (int j = i+1; j < Tb.size(); j++) {
                        if(Tb.get(i).lexema.equals(Tb.get(j).lexema) && Tb.get(i).escopo<=Tb.get(j).escopo){
                            if(Tb.get(j).oper!=null){
                                if(Tb.get(j).oper.equals("Decl")){
                                    qtd++;
                                }
                            }
                        }
                    }
                }
            }
            if(qtd>=1){
                Erro e=new Erro(Tb.get(conta).linha, "Variavel ("+Tb.get(i).lexema+") Declarada mais de uma vez");
                listaErro.add(e);
            }
        }
    }  
    private int VerificaQdtVaria(String id)
    {
        int qtd=0;
        for (int j = 0; j < Tb.size(); j++) {
            if(id.equals(Tb.get(j).lexema)){
                if(Tb.get(j).oper!=null){
                    if(Tb.get(j).oper.equals("Decl")){
                        qtd++;
                    }
                }
            }
        }      
        return qtd;
    }
    private void validaAtrInteira(String v)
    {
        if(v!=null){
            if(!v.equals("") )
            {
                String valor= v.substring(1);
                StringTokenizer st=new StringTokenizer(valor, "+-*/%;");
                while(st.hasMoreTokens()){
                    String a = st.nextToken();
                    if(!a.matches("[0-9]+"))
                    {
                        String type = qualTipoDados(a);
                        if(type!=null)
                        {
                            if(!type.equals("int") && !type.equals("char") && !type.equals("long") && !type.equals("byte")){
                                Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada ("+Tb.get(conta).linha+")");
                                listaErro.add(e);
                            }
                        }
                        else
                        {
                            if(a.contains("\"")||a.contains("\'")){
                                Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada ("+Tb.get(conta).linha+")");
                                listaErro.add(e);
                            }else{
                                Erro e=new Erro(Tb.get(conta).linha, "Variavel "+a+" não declarada");
                                listaErro.add(e);
                            }
                        }
                    }
                }
            }
        }
    }    
    private void validaAtrChar(String v)
    {
        String valor= v.substring(1);
        StringTokenizer st=new StringTokenizer(valor, "+-*/%;");
        while(st.hasMoreTokens()){
            String a = st.nextToken();
            if(a.contains("'")){
                if(a.length()!=3){
                    Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada"+Tb.get(conta).linha);
                    listaErro.add(e);
                }
            }else{
                if(!a.matches("[0-9]+")){
                    String type = qualTipoDados(a);
                    if(!type.equals("int")&&!type.equals("char")){
                        Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada"+Tb.get(conta).linha);
                        listaErro.get(conta);
                    }
                }
            }
        }
    }    
    private void validaReais(String v)
    {
        String valor= v.substring(1);
        StringTokenizer st=new StringTokenizer(valor, "+-*/%;");
        while(st.hasMoreTokens()){
            String a = st.nextToken();
            if(!a.matches("[0-9]+.[0-9]+")&&!a.matches("[0-9]+")){
                String type = qualTipoDados(a);
                if(type!=null)
                {
                    if(!type.equals("int")&&!type.equals("char")&&!type.equals("float")&&!type.equals("double")){
                        Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada "+Tb.get(conta).linha);
                        listaErro.add(e);
                    }
                }
                else
                {
                    if(a.contains("\"")||a.contains("\'")){
                        Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada ("+Tb.get(conta).linha+")");
                        listaErro.add(e);
                    }else{
                        Erro e=new Erro(Tb.get(conta).linha, "Variavel "+a+" não declarada");
                        listaErro.add(e);
                    }
                }
            }
        }
    }
    private void validaString(String v)
    {
        String valor= v.substring(1);
        StringTokenizer st=new StringTokenizer(valor, "+;");
        while(st.hasMoreTokens())
        {
            String a = st.nextToken();
            if(!a.matches("^\".*\"$")){
                String type=qualTipoDados(a);
                if(type!=null)
                {
                    if(!type.equals("String")){
                        Erro e=new Erro(Tb.get(conta).linha, "Expressão de atribuição errada "+Tb.get(conta).linha);
                        listaErro.add(e);
                    }
                }else{
                    Erro e=new Erro(Tb.get(conta).linha, "Variavel "+a+" desconhecida ");
                    listaErro.add(e);
                }
            }
        }
    }
    private String qualTipoDados(String var)
    {
        for (Simbolo Tb1 : Tb) {
            if (Tb1.oper != null && (Tb1.oper.equals("Decl") && Tb1.lexema.equals(var))) {
                return Tb1.tipo;
            }
        }
        return null;
    }
    private void validaBoolean(String valor) 
    {
        char p=12;
//        if(!v.equals(""))
        {
//            String valor= v.substring(1);
        }
    }
    private void ExpressaBoolean() 
    {
        if(conta<Tb.size()){
            conta++;
            if(conta<Tb.size()){
                
                while(!Tb.get(conta).lexema.equals(")") && stop)
                {
                    if(Tb.get(conta).token.equals("OperLo")){
                        conta++;
                    }
                    else if(Tb.get(conta).token.equals("ID"))
                    {
                        int qt = VerificaQdtVaria(Tb.get(conta).lexema);
                        if(qt==0)
                        {
                            Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                            listaErro.add(e);
                            stop=false;
                        }
                        else
                        {
                            String type=qualTipoDados(Tb.get(conta).lexema);
                            if(type.equals("int") || type.equals("char") || type.equals("long") ||type.equals("byte")
                                                  || type.equals("float") || type.equals("double"))
                            {
                                conta++;
                                if(conta<Tb.size()){
                                    if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt")){
                                        while(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt"))
                                        {
                                            if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt")){
                                                conta++;                                            
                                                if(Tb.get(conta).token.contains("Num")){
                                                    conta++;
                                                }else if(Tb.get(conta).token.equals("ID")){
                                                    int qt2 = VerificaQdtVaria(Tb.get(conta).lexema);
                                                    if(qt2==0){
                                                        Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                                                        listaErro.add(e);
                                                    }
                                                    else
                                                    {
                                                        String type2=qualTipoDados(Tb.get(conta).lexema);
                                                        if(type2.equals("int") || type2.equals("char") || type2.equals("long") ||type2.equals("byte")
                                                           || type2.equals("float") || type2.equals("double"))
                                                        {
                                                            conta++;
                                                        }else{
                                                            Erro e=new Erro(Tb.get(conta).linha, "Esperava um tipo de dados númerio "+Tb.get(conta).lexema);
                                                            listaErro.add(e);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        Erro e=new Erro(Tb.get(conta).linha, "Esperava um Operador Relacional ou aritmetrico");
                                        listaErro.add(e);
                                    }
                                }
                            }
                            else if(type.equals("boolean"))
                            {
                                conta++;
                                if(conta<Tb.size()){
                                    if(!Tb.get(conta).lexema.equals(")")){
                                        if(Tb.get(conta).token.equals("OperLo")){
                                            conta++;
                                        }else{
                                            Erro e=new Erro(Tb.get(conta).linha, "Esperava um Operador logico");
                                            listaErro.add(e);
                                        }
                                    }
                                }
                            }
                            else if(type.equals("String")){
                                conta++;
                                if(conta<Tb.size())
                                {
                                    while(Tb.get(conta).lexema.equals("=!") || Tb.get(conta).lexema.equals("==")){
                                        conta++;
                                        String type3=qualTipoDados(Tb.get(conta).lexema);
                                        if(type3.equals("String"))
                                        {
                                            conta++;
                                        }else{
                                            Erro e=new Erro(Tb.get(conta).linha, "Esperava um variave do tipo String");
                                            listaErro.add(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if(Tb.get(conta).token.contains("Num"))
                    {
                        conta++;
                        if(conta<Tb.size())
                        {
                            if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt")){
                                while(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt"))
                                {
                                    if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt"))
                                    {
                                        conta++;                                            
                                        if(Tb.get(conta).token.contains("Num"))
                                        {
                                            conta++;
                                        }
                                        else if(Tb.get(conta).token.equals("ID"))
                                        {
                                            int qt2 = VerificaQdtVaria(Tb.get(conta).lexema);
                                            if(qt2==0)
                                            {
                                                Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                                                listaErro.add(e);
                                            }
                                            else
                                            {
                                                String type2=qualTipoDados(Tb.get(conta).lexema);
                                                if(type2.equals("int") || type2.equals("char") || type2.equals("long") ||type2.equals("byte")
                                                   || type2.equals("float") || type2.equals("double"))
                                                {
                                                    conta++;
                                                }
                                                else
                                                {
                                                    Erro e=new Erro(Tb.get(conta).linha, "Esperava um tipo de dados númerio "+Tb.get(conta).lexema);
                                                    listaErro.add(e);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                Erro e=new Erro(Tb.get(conta).linha, "Esperava um Operador Relacional ou aritmetrico");
                                listaErro.add(e);
                            }
                        }
                    }else if(Tb.get(conta).lexema.equals("true")||Tb.get(conta).lexema.equals("false")){
                        conta++;
                        if(conta<Tb.size()){
                            if(!Tb.get(conta).lexema.equals(")")){
                                if(Tb.get(conta).token.equals("OperLo")){
                                    conta++;
                                }else{
                                    Erro e=new Erro(Tb.get(conta).linha, "Esperava um operador logico");
                                    listaErro.add(e);
                                }
                            }
                        }
                    }                    
                }
                conta++;
            }
        }
    }
    private void ExpressaBoolean2() 
    {
        if(conta<Tb.size()){
            conta++;
            if(conta<Tb.size()){
                
                while(!Tb.get(conta).lexema.equals(";") && stop)
                {
                    if(Tb.get(conta).token.equals("OperLo")){
                        conta++;
                    }
                    else if(Tb.get(conta).token.equals("ID"))
                    {
                        int qt = VerificaQdtVaria(Tb.get(conta).lexema);
                        if(qt==0)
                        {
                            Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                            listaErro.add(e);
                            stop=false;
                        }
                        else
                        {
                            String type=qualTipoDados(Tb.get(conta).lexema);
                            if(type.equals("int") || type.equals("char") || type.equals("long") ||type.equals("byte")
                                                  || type.equals("float") || type.equals("double"))
                            {
                                conta++;
                                if(conta<Tb.size()){
                                    if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt")){
                                        while(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt"))
                                        {
                                            if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt")){
                                                conta++;                                            
                                                if(Tb.get(conta).token.contains("Num")||Tb.get(conta).token.equals("Info")){
                                                    conta++;
                                                }else if(Tb.get(conta).token.equals("ID")){
                                                    int qt2 = VerificaQdtVaria(Tb.get(conta).lexema);
                                                    if(qt2==0){
                                                       Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                                                       listaErro.add(e);
                                                    }
                                                    else
                                                    {
                                                        String type2=qualTipoDados(Tb.get(conta).lexema);
                                                        if(type2.equals("int") || type2.equals("char") || type2.equals("long") ||type2.equals("byte")
                                                           || type2.equals("float") || type2.equals("double"))
                                                        {
                                                            conta++;
                                                        }else{
                                                            Erro e=new Erro(Tb.get(conta).linha, "Esperava um tipo de dados númerio "+Tb.get(conta).lexema);
                                                            listaErro.add(e);
                                                        }
                                                    }
                                                }else{
                                                    Erro e=new Erro(Tb.get(conta).linha, "Expressão logica má escrita");
                                                    listaErro.add(e);
                                                    conta++;
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        Erro e=new Erro(Tb.get(conta).linha, "Esperava um Operador Relacional ou aritmetrico");
                                        listaErro.add(e);
                                    }
                                }
                            }
                            else if(type.equals("boolean"))
                            {
                                conta++;
                                if(conta<Tb.size()){
                                    if(!Tb.get(conta).lexema.equals(")")){
                                        if(Tb.get(conta).token.equals("OperLo")){
                                            conta++;
                                        }else{
                                            Erro e=new Erro(Tb.get(conta).linha, "Esperava um Operador logico");
                                            listaErro.add(e);
                                        }
                                    }
                                }
                            }
                            else if(type.equals("String")){
                                conta++;
                                if(conta<Tb.size())
                                {
                                    while(Tb.get(conta).lexema.equals("=!") || Tb.get(conta).lexema.equals("==")){
                                        conta++;
                                        String type3=qualTipoDados(Tb.get(conta).lexema);
                                        if(type3.equals("String"))
                                        {
                                            conta++;
                                        }else{
                                            Erro e=new Erro(Tb.get(conta).linha, "Esperava um variave do tipo String");
                                            listaErro.add(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if(Tb.get(conta).token.contains("Num"))
                    {
                        conta++;
                        if(conta<Tb.size())
                        {
                            if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt")){
                                while(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt"))
                                {
                                    if(Tb.get(conta).token.equals("OperRela")||Tb.get(conta).token.equals("OperArt"))
                                    {
                                        conta++;                                            
                                        if(Tb.get(conta).token.contains("Num"))
                                        {
                                            conta++;
                                        }
                                        else if(Tb.get(conta).token.equals("ID"))
                                        {
                                            int qt2 = VerificaQdtVaria(Tb.get(conta).lexema);
                                            if(qt2==0)
                                            {
                                                Erro e=new Erro(Tb.get(conta).linha, "Variavel "+Tb.get(conta).lexema+" não declarada");
                                                listaErro.add(e);
                                            }
                                            else
                                            {
                                                String type2=qualTipoDados(Tb.get(conta).lexema);
                                                if(type2.equals("int") || type2.equals("char") || type2.equals("long") ||type2.equals("byte")
                                                   || type2.equals("float") || type2.equals("double"))
                                                {
                                                    conta++;
                                                }
                                                else
                                                {
                                                    Erro e=new Erro(Tb.get(conta).linha, "Esperava um tipo de dados númerio "+Tb.get(conta).lexema);
                                                    listaErro.add(e);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                Erro e=new Erro(Tb.get(conta).linha, "Esperava um Operador Relacional ou aritmetrico");
                                listaErro.add(e);
                            }
                        }
                    }else if(Tb.get(conta).lexema.equals("true")||Tb.get(conta).lexema.equals("false")){
                        conta++;
                        if(conta<Tb.size()){
                            if(!Tb.get(conta).lexema.equals(")")){
                                if(Tb.get(conta).token.equals("OperLo")){
                                    conta++;
                                }else{
                                    Erro e=new Erro(Tb.get(conta).linha, "Esperava um operador logico");
                                    listaErro.add(e);
                                }
                            }
                        }
                    }                    
                }
                conta++;
            }
        }
    }
    
    public void         Imprimir(JTable tabela)
    {
        DefaultTableModel m=(DefaultTableModel)tabela.getModel();
        m.setNumRows(0);
           if(listaErro.size()>0)
           {
               tabela.setForeground(Color.RED);
               for(Erro r : listaErro){                  
                   m.addRow(new Object[]{
                       r.espc,
                       r.linha,
                   });                   
               }
           }
           else
           {
               tabela.setForeground(Color.GREEN);
               m.addRow(new Object[]{
                   "BUILD SUCCESSFUL (total time: 1 second)",
                   ""
               });
           }
       }
}