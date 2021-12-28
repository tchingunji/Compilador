/*
 * To change this license header, choose License ff
 *ffHeaders in Project Properties.
 * To change this template file, choose Tools | Templates
 * and
  35fi if/*open the template in the editor.
 */
package compilador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 **
 * @author Tchingunji
 * ds
 * ****sdd
 */
public class AnaLex {
            
    static String ajuda="";
    static ArrayList<String>palaReservada=iniWordReser();
    public static ArrayList<Lexico>Tabela=new ArrayList<>();
    public static ArrayList<Erro> listaErro=new ArrayList<>();
    public AnaLex(){
        listaErro=new ArrayList<>();
        listaErro.clear();
        Tabela=new ArrayList<>();
        ajuda="";
    }
    static String buscarCodeFonte(){
        String linha="";
        try {
            File f=new File("code.txt");
            if(!f.exists())
                f.createNewFile();          
            BufferedReader br=new BufferedReader(new FileReader(f));            
            while(br.ready()){
                linha=linha+"\n"+br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnaLex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnaLex.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linha;
    }    
    static void ler_caractere(String fonte){
        StringTokenizer parte=new StringTokenizer(fonte, "\n");
        int linha=1;
        while(parte.hasMoreTokens()){
            q0(parte.nextToken(), linha);
            linha++;
        }
    }    
    public static ArrayList<String> iniWordReser(){
        ArrayList<String> lista=new ArrayList<>();
        try 
        {                        
            BufferedReader br=new BufferedReader(new FileReader("comandos.txt"));
            while(br.ready())
            {
                lista.add(br.readLine());
            }                        
        } 
        catch (IOException ex) 
        {
            System.out.println("ERRO de LEXICO");
        }
        return lista;
    }    
    static void q0(String simbolo,int linha){
        ajuda="";
        if(simbolo.length()>0)
        {
            if( String.valueOf(simbolo.charAt(0)).matches("[a-z]|[A-Z]|_"))
            {                
                ajuda=ajuda+String.valueOf(simbolo.charAt(0));
                q1(simbolo.substring(1), 1, linha);
            }
            else if(simbolo.charAt(0)==';')
            {
                Lexico lex=new Lexico("PontVirg", ";", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)==',')
            {
                Lexico lex=new Lexico("Virg", ",", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)==':')
            {
                Lexico lex=new Lexico("DoisPonto", ":", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='{')
            {                
                Lexico lex=new Lexico("AbriChave", "{", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){                    
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='}')
            {
                Lexico lex=new Lexico("FechaChave", "}", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='(')
            {
                Lexico lex=new Lexico("AbrirParen", "(", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)==')')
            {
                Lexico lex=new Lexico("FecharParen", ")", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='[')
            {
                Lexico lex=new Lexico("AbrirParenReto", "[", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)==']')
            {
                Lexico lex=new Lexico("FecharParenReto", "]", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(String.valueOf(simbolo.charAt(0)).matches("[0-9]"))
            {
                ajuda =ajuda+String.valueOf(simbolo.charAt(0));
                q2(simbolo.substring(1), linha);
            }
            else if(simbolo.charAt(0)=='+')
            {
                ajuda =ajuda+"+";
                q4(simbolo.substring(1),linha);
            }
            else if(simbolo.charAt(0)=='-')
            {
                ajuda =ajuda+"-";
                q5(simbolo.substring(1),linha);
            }
            else if(simbolo.charAt(0)=='*')
            {
                ajuda="*";
                qMul(simbolo.substring(1), linha);                
            }
            else if(simbolo.charAt(0)=='/')
            {
                ajuda="/";
                q6(simbolo.substring(1), linha);
            }
            else if(simbolo.charAt(0)=='=')
            {
                ajuda="=";
                q7(simbolo.substring(1), linha);
            }
            else if(simbolo.charAt(0)=='.')
            {
                Lexico lex=new Lexico("Ponto", ".", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='%')
            {
                Lexico lex=new Lexico("OperArt", "%", linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }else if(simbolo.charAt(0)=='&')
            {
                if(simbolo.charAt(1)=='&')
                {
                    Lexico lex=new Lexico("OperLo", "&&", linha);
                    Tabela.add(lex);
                    ajuda="";
                    if(simbolo.length()>1){
                        q0(simbolo.substring(2), linha);
                    }
                }
            }
            else if(simbolo.charAt(0)=='\"')
            {
                q8(simbolo.substring(1),linha);
            }
            else if(simbolo.charAt(0)=='<')
            {
                ajuda="<";
                if(simbolo.length()>1){
                    q9(simbolo.substring(1), linha);
                }else{
                    Lexico lex=new Lexico("OperRela", ajuda, linha);
                    Tabela.add(lex);
                    ajuda="";
                }
            }
            else if(simbolo.charAt(0)=='>')
            {
                ajuda=">";
                if(simbolo.length()>1){
                    q9(simbolo.substring(1), linha);
                }else{
                    Lexico lex=new Lexico("OperRela", ajuda, linha);
                    Tabela.add(lex);
                    ajuda="";
                }
            }
            else if(simbolo.charAt(0)=='|')
            {
                if(simbolo.charAt(1)=='|')
                {
                    Lexico lex=new Lexico("OperLo", "||", linha);
                    Tabela.add(lex);
                    ajuda="";
                    if(simbolo.length()>1){
                        q0(simbolo.substring(2), linha);
                    }
                }
            }
            else if(simbolo.charAt(0)=='\''){
                ajuda=ajuda+"'";
                q11(simbolo.substring(1), linha);
            }
            else
            {
                if(!String.valueOf(simbolo.charAt(0)).matches("\\s"))
                {
                    Erro e=new Erro(linha, "Erro caracter desconhecido "+simbolo.charAt(0));
                    listaErro.add(e);
                    System.err.println("Erro caracter desconhecido "+simbolo.charAt(0));
                }
                else
                {
                    if(simbolo.length()>0)
                    {
                        q0(simbolo.substring(1), linha);
                    }
                }
            }
        }        
    }    
    static void q1(String simbolo, int local, int linha){
        if(simbolo.length()>0){
            if( String.valueOf(simbolo.charAt(0)).matches("[a-z]|[A-Z]|_|[0-9]")){
                ajuda=ajuda+String.valueOf(simbolo.charAt(0));
                if(simbolo.length()>0){
                    q1(simbolo.substring(1), local, linha);
                }else{
                    Lexico lex=new Lexico("ID", ajuda, linha);
                    Tabela.add(lex);
                    ajuda="";
                }
            }
            else
            {
                Lexico lex=new Lexico("ID", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("ID", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }
    static void q2(String simbolo, int linha){
        if(simbolo.length()>0){
            if(String.valueOf(simbolo.charAt(0)).matches("[0-9]"))
            {
                ajuda =ajuda+String.valueOf(simbolo.charAt(0));            
                q2(simbolo.substring(1), linha);
            }
            else if(simbolo.charAt(0)=='.')
            {
                ajuda=ajuda+".";
                q3(simbolo.substring(1),linha);
            }
            else if(String.valueOf(simbolo.charAt(0)).matches("[a-zA-Z]"))
            {
                System.err.println("Erro número mau escrito");
            }
            else
            {
                Lexico lex=new Lexico("NumInt", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                q0(simbolo, linha);
            }
        }
        else
        {
            Lexico lex=new Lexico("NumInt", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }    
    static void q3(String simbolo, int linha) {
        if(simbolo.length()>0){
            if(String.valueOf(simbolo.charAt(0)).matches("[0-9]"))
            {
                ajuda =ajuda+String.valueOf(simbolo.charAt(0));
                q3(simbolo.substring(1), linha);
            }
            else if(String.valueOf(simbolo.charAt(0)).matches("[a-zA-Z]"))
            {
                System.err.println("Erro número mau escrito");
            }
            else
            {
                Lexico lex=new Lexico("NumReal", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("NumReal", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
            if(simbolo.length()>0){
                q0(simbolo, linha);
            }
        }
    }
    static void q4(String simbolo, int linha){
        if(simbolo.length()>0){
            if(String.valueOf(simbolo.charAt(0)).equals("+"))
            {
                ajuda=ajuda+"+";
                Lexico lex=new Lexico("Increment", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(String.valueOf(simbolo.charAt(0)).equals("="))
            {
                ajuda=ajuda+"=";
                Lexico lex=new Lexico("Adiseguida", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else
            {
                Lexico lex=new Lexico("OperArt", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("OperArt", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }
    static void q5(String simbolo, int linha){
        if(simbolo.length()>0){
            if(String.valueOf(simbolo.charAt(0)).equals("-")){
                ajuda=ajuda+"-";
                Lexico lex=new Lexico("Decrement", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(String.valueOf(simbolo.charAt(0)).equals("="))
            {
                ajuda=ajuda+"=";
                Lexico lex=new Lexico("Subseguida", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else
            {
                Lexico lex=new Lexico("OperArt", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("OperArt", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }    
    static void qMul(String simbolo, int linha){
        if(simbolo.length()>0){
            if(simbolo.charAt(0)=='=')
            {
                ajuda=ajuda+"=";
                Lexico lex=new Lexico("Mulseguida", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }            
            else
            {
                Lexico lex=new Lexico("OperArt", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0)
                {
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("OperArt", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }    
    static void q6(String simbolo, int linha){
        if(simbolo.length()>0){
            if(simbolo.charAt(0)=='=')
            {
                ajuda=ajuda+"=";
                Lexico lex=new Lexico("divseguida", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='/'){
                ajuda=ajuda+"/";
                q10(simbolo.substring(1),linha);
            }
            else
            {
                Lexico lex=new Lexico("OperArt", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0)
                {
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("OperArt", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }
    static void q7(String simbolo, int linha){
        if(simbolo.length()>0){
            if(simbolo.charAt(0)=='=')
            {
                ajuda =ajuda+"=";
                Lexico lex=new Lexico("OperRela", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else if(simbolo.charAt(0)=='!')
            {
                ajuda =ajuda+"!";
                Lexico lex=new Lexico("OperRela", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo.substring(1), linha);
                }
            }
            else
            {
                Lexico lex=new Lexico("Igualdade", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0){
                    q0(simbolo, linha);
                }
            }
        }
        else
        {
            Lexico lex=new Lexico("Igualdade", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }
    static void q8(String simbolo, int linha){
        if(simbolo.length()>0)
        {
            if(simbolo.charAt(0)!='\"')
            {
                ajuda=ajuda+String.valueOf(simbolo.charAt(0));
                q8(simbolo.substring(1), linha);
            }
            else
            {
                ajuda = '\"'+ajuda+'\"';
                Lexico lex=new Lexico("Infos", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                if(simbolo.length()>0)
                {
                    q0(simbolo.substring(1),linha);
                }
            }
        }
    }
    static void q9(String simbolo, int linha){
        if(simbolo.length()>0){
            if(simbolo.charAt(0)=='='){
                ajuda=ajuda+"=";
                Lexico lex=new Lexico("OperRela", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                q0(simbolo.substring(1), linha);
            }else{
                Lexico lex=new Lexico("OperRela", ajuda, linha);
                Tabela.add(lex);
                ajuda="";
                q0(simbolo, linha);
            }
        }
    }
    static void q10(String simbolo, int linha){
        
        if(simbolo.length()>0){
            ajuda =ajuda+simbolo.charAt(0);
            q10(simbolo.substring(1),linha);
        }else{
            Lexico lex=new Lexico("Coment", ajuda, linha);
            Tabela.add(lex);
            ajuda="";
        }
    }
    static void q11(String simbolo,int linha){
        if(simbolo.length()>0)
        {
            if(simbolo.charAt(0)!='\''){
                ajuda=ajuda+String.valueOf(simbolo.charAt(0));
                q11(simbolo.substring(1),linha);
            }else{
                Lexico lx=new Lexico("Info", ajuda+"'", linha);
                Tabela.add(lx);
                ajuda="";
                if(simbolo.length()>0)
                {
                    q0(simbolo.substring(1),linha);
                }
            }
        }
    }
    static boolean valiPalaReservada(String palavra){
        for (String palaReservada1 : palaReservada) {
            if(palavra.equals(palaReservada1))
                return true;
        }
        return false;
    }
    static void verficaPalavrasREservadas(){
        
       for (int i = 0; i < Tabela.size(); i++) {
           if(Tabela.get(i).token.equalsIgnoreCase("ID")){
               String palavra = Tabela.get(i).lexema;
               if(valiPalaReservada(palavra)){
                   Tabela.get(i).token="PalaReser"+Tabela.get(i).lexema;
               }
           }
       }
    }
    public  void AnalizaLexico() {
        Tabela.clear();
        ler_caractere(buscarCodeFonte());
        
        verficaPalavrasREservadas();
        DefaultTableModel model=(DefaultTableModel)Ini.tabela.getModel();
        model.setNumRows(0);
        for (int i = 0; i < Tabela.size(); i++) {
            model.addRow(new Object[]{
                Tabela.get(i).lexema,
                Tabela.get(i).token,
                Tabela.get(i).linha,
            });        
        }
    }
} 
