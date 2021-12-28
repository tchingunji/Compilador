package compilador;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Sintatico {
    
    static int conta=0,escopo;
    static String tipo;
    private final ArrayList<Lexico> tb;
    public static ArrayList<Erro> listaErro=new ArrayList<>();
    public static ArrayList<Simbolo>TbSimbolo=new ArrayList<>();
    private boolean on=false;
    
    public Sintatico()                      {        
        conta=0;
        escopo=0;
        listaErro.clear();
        tb=AnaLex.Tabela;
        TbSimbolo.clear();
//        TbSimbolo=new ArrayList<>();
//        listaErro= AnaLex.listaErro;        
        InicioCompilacao();
        DeclaracaoClasse();
        while(conta<tb.size()-1)
        {
            CorpoClass(); 
        }
        FechaChaveta1();
    }
    private void        InicioCompilacao()  {
        Pakcage();
        Imports();
    }    
    private void        Pakcage()           {
        if(tb.get(conta).lexema.equals("package")){
            conta++;
            Nome();
        }else{
            Erro e=new Erro(tb.get(conta).linha,"Esperava palavra package");
            listaErro.add(e);
        }
    }
    private void        Imports()           {
        if(tb.get(conta).lexema.equals("import")){
            conta++;
            Nome();
        }
    }
    private void        Nome()              {
        if(conta<tb.size()){
            if(tb.get(conta).token.equals("ID")){
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).lexema.equals(".")||tb.get(conta).lexema.equals("_")){
                        conta++;
                        Nome();
                    }else if(tb.get(conta).lexema.equals(";"))
                    {
                        conta++;
                    }
                }else{
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava um ;");
                    listaErro.add(e);
                }
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador");
            listaErro.add(e);
        }
    }    
    private void        NomeClasse()        {
        if(conta<tb.size()){
            if(tb.get(conta).token.equals("ID")){
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).lexema.equals(".")||tb.get(conta).lexema.equals("_")){
                        conta++;
                        NomeClasse();
                    }
                    else if(tb.get(conta).lexema.equals("{")){
                        conta++;
                        escopo=1;
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um {");
                        listaErro.add(e);
                    }
                }else{
                   Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um identificador ou número");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador");
            listaErro.add(e);
        }
    }
    private boolean     Modificador()       {
        if(tb.get(conta).lexema.equals("public")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("private")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("protected")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("abstract")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("final")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("volatile")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("transient")){
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("synchronized")){
            conta++;
            return true;
        }
        return false;
    }
    private boolean     TipoDados()         {
        if(tb.get(conta).lexema.equals("int")){
            tipo="int";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("char")){
            tipo="char";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("String")){
            tipo="String";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("float")){
            tipo="float";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("double")){
            tipo="double";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("short")){
            tipo="short";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("long")){
            tipo="long";
            conta++;
            return true;
        }else if(tb.get(conta).lexema.equals("byte")){
            tipo="byte";
            conta++;
            return true;
        }
        else if(tb.get(conta).lexema.equals("void")){
            tipo="void";
            conta++;
            return true;
        }
        else if(tb.get(conta).lexema.equals("boolean")){
            tipo="boolean";
            conta++;
            return true;
        }
        return false;
    }    
    private boolean     AbreChaveta1()      {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("{")){
                conta++;
                escopo++;
                return true;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um {");
            listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um {");
            listaErro.add(e);
        }
        return false;
    }
    private boolean     FechaChaveta1()     {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("}")){
                escopo--;
                conta++;
                return true;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um }");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um }");
            listaErro.add(e);
        }
        return false;
    }
    private boolean     FechaPArenteses1()  {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals(")")){
                conta++;
                return true;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um )");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um )");
            listaErro.add(e);
        }
        return false;
    }
    private boolean     FechaPArenteses2()  {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals(")")){
                conta++;
                return true;
            }
        }
        return false;
    }
    private boolean     AbrePArenteses1()   {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("(")){
                conta++;
                return true;
            }
        }
        return false;
    }
    private boolean     AbrePArentesesRetos1(){
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("[")){
                conta++;
                return true;
            }
        }
        return false;
    }
    private boolean     FechaPArentesesRetos1(){
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("]")){                
                conta++;
                return true;
            }
        }
        return false;
    }
    private boolean     PontoVirgula()      {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals(";")){
                conta++;
                return true;
            }
        }
        return false;
    }
    private boolean     Virgula()           {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals(",")){
                conta++;
                return true;
            }
        }
        return false;
    }
    private boolean     Igual()             {
    if(conta<tb.size()){
        if(tb.get(conta).lexema.equals("=")){
            conta++;
            return true;
        }
    }
    return false;
}    
    private boolean     New()               {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("new")){
                conta++;
                return true;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar a palavra new");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar a palavra new");
            listaErro.add(e);
        }
        return false;
    } 
    private void        Parametros()        {
        if(conta<tb.size())
        {
            if(TipoDados())
            {
                if(conta<tb.size())
                {                    
                    if(tb.get(conta).token.equals("ID"))
                    {
                        conta++;
                        if(conta<tb.size())
                        {
                            if(tb.get(conta).lexema.equals(","))
                            {
                                conta++;
                                Parametros();
                            }
                            else if(tb.get(conta).lexema.equals(")"))
                            {
                                conta++;
                            }
                            else
                            {
                                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um ) ou ,");
                                listaErro.add(e);
                            }
                        }
                        else
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ) ou ;");
                            listaErro.add(e);
                        }
                    }
                    else
                    {
                        Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um identificador");
                        listaErro.add(e);
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador");
                    listaErro.add(e);
                }
            }
            else if(tb.get(conta).lexema.equals(")"))
            {
                conta++;
            }
            else
            {
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar )");
                listaErro.add(e);
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um tipo de dados");
            listaErro.add(e);
        }
    }    
    private void        DeclaracaoClasse()  {
        if(conta<tb.size()){
            Modificador();
            if(conta<tb.size()){
                if(tb.get(conta).lexema.equals("class")){
                    conta++;
                    NomeClasse();
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava a palavra class");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta-1).linha,"Esperava a palavra class");
                listaErro.add(e);
            }
        }else{
           Erro e=new Erro(tb.get(conta-1).linha,"Esperava um modificador de acesso ou a palavra class");
           listaErro.add(e);
        }
    }    
    public  void        Imprimir(JTable t)  {
        DefaultTableModel model=(DefaultTableModel)t.getModel();
        model.setNumRows(0);
        for (Simbolo b : TbSimbolo) {                    
            model.addRow(new Object[]{
                b.lexema,
                b.token,
                b.linha,
                b.escopo,
                b.tipo,
                b.valor,
                b.oper,
            });
        }        
    }
    private boolean     isValor()           {
        if(conta<tb.size()){
            if(tb.get(conta).token.contains("Num") || tb.get(conta).token.contains("Info") || tb.get(conta).token.equals("ID") 
                    ||tb.get(conta).lexema.equals("true")||tb.get(conta).lexema.equals("false")){
                conta++;
                return true;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um valor");
                listaErro.add(e);
                return false;
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Espera um valor");
            listaErro.add(e);
            return false;
        }
    }
    private boolean     isValor2()           {
        if(conta<tb.size()){
            if(tb.get(conta).token.contains("Num") || tb.get(conta).token.contains("Info") || tb.get(conta).token.equals("ID") 
                    ||tb.get(conta).lexema.equals("true")||tb.get(conta).lexema.equals("false")){
                conta++;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean     isID()              {
        if(conta<tb.size()){
            if("ID".equals(tb.get(conta).token)){
                conta++;
                return true;
            }else{
                return false;
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Espera um identificador");
            listaErro.add(e);
            return false;
        }
    }
    private boolean     isNum()             {
        if(conta<tb.size()){
            if(tb.get(conta).token.contains("Num")){
                conta++;
                return true;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Espera um número");
                listaErro.add(e);
                return false;
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Espera um número");
            listaErro.add(e);
            return false;
        }
    }
    private void        DeclaracaoVariavel(){
        if(isID()){
            Simbolo b=tb.get(conta-1);
            b.escopo=escopo;
            b.tipo=tipo;
            b.escopo=escopo;
            b.oper="Decl";
            if(AbrePArentesesRetos1()){
                if(FechaPArentesesRetos1()){
                    if(Igual()){
                        New();
                        if(TipoDados()){
                            if(AbrePArentesesRetos1()){
                                isNum();
                                if(FechaPArentesesRetos1()){
                                    if(PontoVirgula()){
//                                        conta++;
                                    }else if (Virgula()) {
                                        DeclaracaoVariavel();
                                    }else{
                                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ; ou ,");
                                        listaErro.add(e);
                                    }
                                }else{
                                    Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                                    listaErro.add(e);
                                }
                            }else{
                                Erro e=new Erro(tb.get(conta).linha,"Esperava abre parenteses reto");
                                listaErro.add(e);
                            }
                        }else{
                            Erro e=new Erro(tb.get(conta).linha,"Esperava um tipo de dados");
                            listaErro.add(e);
                        }
                    }else if(PontoVirgula()){//x[],
                        
                        TbSimbolo.add(b);
//                      conta++;
                    }else if(Igual()){
                        DeclaracaoVariavel();
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Sintaxe incorrecta");
                        listaErro.add(e);
                    }
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                    listaErro.add(e);
                }
            }else if(Virgula()){//x,i
                TbSimbolo.add(b);
                b=tb.get(conta-1);
                TbSimbolo.add(b);
                DeclaracaoVariavel();
            }else if(Igual()){//x=12
                boolean status = isValor();//x=VALOR,
                if(status){
                    b.valor="="+tb.get(conta-1).lexema;
                    TbSimbolo.add(b);
                }
                
                if(PontoVirgula()){//x=VALOR;
//                    conta++;
                    b=tb.get(conta-1);
                    TbSimbolo.add(b);
                }else if (Virgula()) {//x=VALOR,
                    b=tb.get(conta-1);
                    TbSimbolo.add(b);
                    DeclaracaoVariavel();
                }
                else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava uma, ou ;");
                    listaErro.add(e);
                }
            }else if(PontoVirgula()){//x;
                TbSimbolo.add(b);
                b=tb.get(conta-1);
                TbSimbolo.add(b);
//              conta++;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Espera um , ou ;");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta).linha,"Espera um identificador");
            listaErro.add(e);
        }
    }
    
    private void        DeclaracaoVariavel1(){
        if(isID()){
            Simbolo b=tb.get(conta-1);
            b.escopo=escopo;
            
//            b.tipo=tipo;
//            b.oper="Decl";
            if(AbrePArentesesRetos1()){
                if(FechaPArentesesRetos1()){
                    if(Igual()){
                        New();
                        if(TipoDados()){
                            if(AbrePArentesesRetos1()){
                                isNum();
                                if(FechaPArentesesRetos1()){
                                    if(PontoVirgula()){
//                                        conta++;
                                    }else if (Virgula()) {
                                        DeclaracaoVariavel();
                                    }else{
                                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ; ou ,");
                                        listaErro.add(e);
                                    }
                                }else{
                                    Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                                    listaErro.add(e);
                                }
                            }else{
                                Erro e=new Erro(tb.get(conta).linha,"Esperava abre parenteses reto");
                                listaErro.add(e);
                            }
                        }else{
                            Erro e=new Erro(tb.get(conta).linha,"Esperava um tipo de dados");
                            listaErro.add(e);
                        }
                    }else if(PontoVirgula()){//x[],
                        
                        TbSimbolo.add(b);
//                      conta++;
                    }else if(Igual()){
                        DeclaracaoVariavel();
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Sintaxe incorrecta");
                        listaErro.add(e);
                    }
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                    listaErro.add(e);
                }
            }else if(Virgula()){//x,i
                TbSimbolo.add(b);
                b=tb.get(conta-1);
                TbSimbolo.add(b);
                DeclaracaoVariavel();
            }else if(Igual()){//x=12
                boolean status = isValor();//x=VALOR,
                if(status){
                    b.valor=tb.get(conta-1).lexema;
                    TbSimbolo.add(b);
                }
                
                if(PontoVirgula()){//x=VALOR;
//                    conta++;
                    b=tb.get(conta-1);
                    TbSimbolo.add(b);
                }else if (Virgula()) {//x=VALOR,
                    b=tb.get(conta-1);
                    TbSimbolo.add(b);
                    DeclaracaoVariavel();
                }
                else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava uma, ou ;");
                    listaErro.add(e);
                }
            }else if(PontoVirgula()){//x;
                TbSimbolo.add(b);
                b=tb.get(conta-1);
                    TbSimbolo.add(b);
//              conta++;
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Espera um , ou ;");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta).linha,"Espera um identificador");
            listaErro.add(e);
        }
    }
    
    private void        CorpoClass()        {
        if(Modificador()){
            if(TipoDados()){
                if(isID()){
                    Simbolo b = tb.get(conta-1);
                    b.escopo=escopo;
                    b.tipo=tipo;
                    b.oper="Decl";
                    if(AbrePArenteses1()){
                        Parametros();
                        AbreChaveta1();//public int X(int x){
                        while(!tb.get(conta).lexema.equals("}")){
                            Bloco();
                        }
                        FechaChaveta1();//public int X(int x){qualquer coisa}
                    }else if(AbrePArentesesRetos1()){
                        if(FechaPArentesesRetos1()){
                            
                            if(Igual()){
                                New();
                                if(TipoDados()){
                                    if(AbrePArentesesRetos1()){
                                        isNum();
                                        if(FechaPArentesesRetos1()){
                                            if(PontoVirgula()){
//                                                conta++;
                                            }else if (Virgula()) {
                                                DeclaracaoVariavel();
                                            }else{
                                                Erro e=new Erro(tb.get(conta).linha,"Esperava um ; ou ,");
                                                listaErro.add(e);
                                            }
                                        }else{
                                            Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                                            listaErro.add(e);
                                        }
                                    }else{
                                        Erro e=new Erro(tb.get(conta).linha,"Esperava abre parenteses reto");
                                        listaErro.add(e);
                                    }
                                }else{
                                    Erro e=new Erro(tb.get(conta).linha,"Esperava um tipo de dados");
                                    listaErro.add(e);
                                }
                            }else if(PontoVirgula()){
                                TbSimbolo.add(b);
                            }else if(Igual()){
                                DeclaracaoVariavel();
                            }else{
                                Erro e=new Erro(tb.get(conta).linha,"Sintaxe incorrecta");
                                listaErro.add(e);
                            }
                        }else{
                            Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                            listaErro.add(e);
                        }
                    }else if(Virgula()){//public int x,i
                        TbSimbolo.add(b);
                        DeclaracaoVariavel();
                    }else if(Igual()){//public int x=12 bsomething
                        boolean status = isValor();
                        if(status){
                            b.valor=tb.get(conta-1).lexema;
                            TbSimbolo.add(b);
                        }
                        if(PontoVirgula()){
//                            conta++;
                        }else if (Virgula()) {
                            DeclaracaoVariavel();
                        }else{
                            Erro e=new Erro(tb.get(conta).linha,"Esperava um , ou ;");
                            listaErro.add(e);
                        }
                    }else if(PontoVirgula()){//public int x;
                        TbSimbolo.add(b);
//                        conta++;
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Espera um , ou ;");
                        listaErro.add(e);
                    }
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Espera um identificador");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Espera um tipo de dados");
                listaErro.add(e);
                conta++;
            }
        }
        else if(TipoDados()){
            if(isID()){
                Simbolo b = tb.get(conta-1);
                b.escopo=escopo;
                b.tipo=tipo;
                b.oper="Decl";
                if(AbrePArenteses1()){
                    Parametros();
                    AbreChaveta1();// int X(int x){
                    while(!tb.get(conta).lexema.equals("}")){
                        Bloco();
                    }
                    FechaChaveta1();//int X(int x){qualquer coisa}
                }else if(AbrePArentesesRetos1()){
                    if(FechaPArentesesRetos1()){
                        if(Igual()){
                            New();
                            if(TipoDados()){
                                if(AbrePArentesesRetos1()){
                                    isNum();
                                    if(FechaPArentesesRetos1()){
                                        if(PontoVirgula()){

                                        }else if (Virgula()) {
                                            DeclaracaoVariavel();
                                        }else{
                                            Erro e=new Erro(tb.get(conta).linha,"Esperava um ; ou ,");
                                            listaErro.add(e);
                                        }
                                    }else{
                                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                                        listaErro.add(e);
                                    }
                                }else{
                                    Erro e=new Erro(tb.get(conta).linha,"Esperava abre parenteses reto");
                                    listaErro.add(e);
                                }
                            }else{
                                Erro e=new Erro(tb.get(conta).linha,"Esperava um tipo de dados");
                                listaErro.add(e);
                            }
                        }else if(PontoVirgula()){

                        }else if(Igual()){
                            DeclaracaoVariavel();
                        }else{
                            Erro e=new Erro(tb.get(conta).linha,"Sintaxe incorrecta");
                            listaErro.add(e);
                        }
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                        listaErro.add(e);
                    }
                }else if(Virgula()){                     
                    TbSimbolo.add(b);
                    DeclaracaoVariavel();
                }else if(Igual()){
                    boolean status = isValor();
                    if(status){
                        b.valor=tb.get(conta-1).lexema;
                        TbSimbolo.add(b);
                     }
                    if(PontoVirgula()){

                    }else if (Virgula()) {
                        DeclaracaoVariavel();
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um , ou ;");
                        listaErro.add(e);
                    }
                }else if(PontoVirgula()){
                    TbSimbolo.add(b);
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Espera um , ou ;");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Espera um identificador");
                listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta).linha,"Espera um tipo de dados");
            listaErro.add(e);
            conta++;
        }       
    }
    private void        Expressao3()        {
       if(conta<tb.size())
       {
           if(tb.get(conta).token.contains("Num")||tb.get(conta).token.contains("Info") ){
               TbSimbolo.add(tb.get(conta));
               conta++;
               if(conta<tb.size())
               {
                   if(tb.get(conta).lexema.equals(")")||tb.get(conta).lexema.equals(";"))
                   {
                       Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um operador");
                       listaErro.add(e);
                   }
                   else if(tb.get(conta).token.equals("OperRela") ||tb.get(conta).token.equals("OperArt"))
                   {
                       TbSimbolo.add(tb.get(conta));
                       conta++;
                       Expressoes2();
                   }
                   else
                   {
                       Expressoes2();
                   }
               }
               else
               {
                   Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um operador"); 
                   listaErro.add(e);
               }
           }
           else
           {
               Expressoes2();
           }
       }
       else
       {
           Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou número");
           listaErro.add(e);
       }
   }    
    private void        Expressoes2()       {
        if(conta<tb.size())
        {
            if(tb.get(conta).token.equals("ID")||tb.get(conta).token.contains("Info")||tb.get(conta).token.contains("Num"))
            {
                TbSimbolo.add(tb.get(conta));
                conta++;
                if(conta<tb.size())
                {
                    if(isOperadorRelaci2(conta))
                    {
                        TbSimbolo.add(tb.get(conta));
                        conta++;
                        Expressoes2();                        
                    }
                    else if(isOperadorAritme2(conta))
                    {
                        TbSimbolo.add(tb.get(conta));
                        conta++;                                   
                        Expressoes2();
                        if(tb.get(conta).token.equals("OperRela")){
                            if(isOperadorRelaci2(conta)){
                                TbSimbolo.add(tb.get(conta));
                                conta++;
                            }else{
                                 Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um operador relacional"); 
                                 listaErro.add(e);
                            }
                        }
                    }
                    else
                    {
                        if(isOperadorLogico2(conta))
                        {
                            TbSimbolo.add(tb.get(conta));
                            conta++;
                            Expressao3();
                        }
                        else if(tb.get(conta).lexema.equals(")"))
                        {
                            
                        }
                        else if(tb.get(conta).token.contains("Oper"))
                        {
                             Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um operador");
                             listaErro.add(e);
                        }
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ; ou operador");
                    listaErro.add(e);
                }
            }
            else if(tb.get(conta).lexema.equals("true")|| tb.get(conta).lexema.equals("false"))
            {
                TbSimbolo.add(tb.get(conta));
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).lexema.equals(")"))
                    {
                        TbSimbolo.add(tb.get(conta));
                    }
                    else if(isOperadorLogico2(conta))
                    {
                        conta++;
                        Expressao3();
                    }
//                    else
//                    {
//                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um operador logico");
//                        listaErro.add(e);
//                    }
                }
                else
                {
                     Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um operador logico ou um )");
                     listaErro.add(e);
                }
            }
            else
            {
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um identificador ou número");
                listaErro.add(e);
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou número1");
            listaErro.add(e);
        }
    }
    private boolean     isOperadorRelaci2(int i)    {
        if(i<tb.size())
        {
            if(tb.get(i).token.equals("OperRela"))
            {
                return true;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um Operador Relacional");           
            listaErro.add(e);
        }
        return false;
    }
    private boolean     isOperadorLogico2(int i)    {
        if(i<tb.size())
        {
            if(tb.get(i).token.equals("OperLo"))
            {
                return true;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um Operador logico");
             listaErro.add(e);
        }
        return false;
    }
    private boolean     isOperadorAritme2(int i)    {
        if(i<tb.size())
        {
            if(tb.get(i).token.equals("OperArt"))
            {
                return true;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um Operador Aritmetrico");
            listaErro.add(e);
        }
        return false;
    } 
    private void        Bloco()                     {
        if(tb.get(conta).lexema.equals("if"))
        {
            TbSimbolo.add(tb.get(conta));
            conta++;                 
            IF();            
        }
        else if(tb.get(conta).lexema.equals("while"))
        {
            TbSimbolo.add(tb.get(conta));
            conta++;                 
            WHILE();
        }
        else if(tb.get(conta).lexema.equals("for"))
        {
            TbSimbolo.add(tb.get(conta));
            conta++;                 
            FOR();
        }
        else if(tb.get(conta).lexema.equals("do"))
        {
            conta++;                 
            DO();
        }
        else if(tb.get(conta).token.equals("ID"))
        {
            Simbolo b=tb.get(conta);
            
            conta++;
            int aux=conta;
            String a="";
            if(on){InicializaVariavel2();b.oper="Decl";b.tipo=tipo;b.escopo=escopo;}
            else{InicializaVariavel();}
            
            for(int k=aux;k<conta;k++){
                a=a+tb.get(k).lexema;
            }
            b.valor=a;
            b.escopo=escopo;
            TbSimbolo.add(b);
        }
        else if(TipoDados()){
            if(isID()){
                Simbolo b = tb.get(conta-1);
                b.escopo=escopo;
                b.tipo=tipo;
                b.oper="Decl";
                if(AbrePArentesesRetos1()){
                    if(FechaPArentesesRetos1()){
                        if(Igual()){
                            New();
                            if(TipoDados()){
                                if(AbrePArentesesRetos1()){
                                    isNum();
                                    if(FechaPArentesesRetos1()){
                                        if(PontoVirgula()){

                                        }else if (Virgula()) {
                                            
                                        }else{
                                            Erro e=new Erro(tb.get(conta).linha,"Esperava um ; ou ,");
                                            listaErro.add(e);
                                        }
                                    }else{
                                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                                        listaErro.add(e);
                                    }
                                }else{
                                    Erro e=new Erro(tb.get(conta).linha,"Esperava abre parenteses reto");
                                    listaErro.add(e);
                                }
                            }else{
                                Erro e=new Erro(tb.get(conta).linha,"Esperava um tipo de dados");
                                listaErro.add(e);
                            }
                        }else if(PontoVirgula()){
                            TbSimbolo.add(b);
                        }else if(Virgula()){  
                            TbSimbolo.add(b);
                        }else{
                            Erro e=new Erro(tb.get(conta).linha,"Sintaxe incorrecta");
                            listaErro.add(e);
                        }
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ]");
                        listaErro.add(e);
                    }
                }else if(Virgula()){ //int x, something
                    TbSimbolo.add(b);
                    on=true;
                    if(isID()){
                        conta--;
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um Identificador");
                        listaErro.add(e);
                    }
                }else if(Igual()){ //int x=12    
                    conta--;
                    int aux=conta;
                    String a="";
                    InicializaVariavel();                    
                        for(int k=aux;k<conta;k++){
                            a=a+""+tb.get(k).lexema;
                        }                   
                    b.valor=a;                   
                    TbSimbolo.add(b);
                    conta--;
                    isValor2();
                    if(Virgula()){
                        on=true;
                    }else if(PontoVirgula()){
                        on=false;
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ; ou ,");
                        listaErro.add(e);
                    }
                    
                }else if(PontoVirgula()){// int x;something
                    TbSimbolo.add(b);
                    on=false;
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Espera um , ou ;");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Espera um identificador");
                listaErro.add(e);
            }
        }else{
            conta++;
        }
    }    
    private boolean     isOperadorRelacional(int i) {
        if(i<tb.size())
        {
            if(tb.get(i).token.equals("OperRela"))
            {
                return true;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(i-1).linha,"Esperava encontrar um Operador Relacional");
            listaErro.add(e);
        }
        return false;
    }
    private boolean     isOperadorLogico(int i)     {
        if(i<tb.size())
        {
            if(tb.get(i).token.equals("OperLo"))
            {
                return true;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(i-1).linha,"Esperava encontrar um Operador logico");
            listaErro.add(e);
        }
        return false;
    }
    private boolean     isOperadorAritme(int i)     {
        if(i<tb.size())
        {
            if(tb.get(i).token.equals("OperArt"))
            {
                return true;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(i-1).linha,"Esperava encontrar um Operador Aritmetrico");
            listaErro.add(e);
        }
        return false;
    }    
    private void        Expressoes()                {
        if(conta<tb.size())
        {
            if(tb.get(conta).token.equals("ID")||tb.get(conta).token.contains("Info")||tb.get(conta).token.contains("Num"))
            {
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).lexema.equals(";"))
                    {
                        conta++;
                    }
                    else if(isOperadorRelacional(conta))
                    {
                        conta++;
                        Expressoes();
                    }
                    else if(isOperadorAritme(conta))
                    {
                        conta++;                                   
                        Expressoes();
                    }
                    else
                    {
                        if(isOperadorLogico(conta))
                        {
                            conta++;
                            Expressoes();
                        }
                        else
                        {
                            Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um ; ou operador");
                            listaErro.add(e);
                        }
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ; ou operador");
                    listaErro.add(e);
                }
            }            
            else if(isOperadorLogico(conta))
            {
                conta++;
                Expressoes();
            }
            else
            {
                Erro e=new Erro(tb.get(conta).linha,"Esperava encontrar um identificador ou número");
                listaErro.add(e);
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou número");
            listaErro.add(e);
        }
    }
    private void        InicializaVariavel()        {
        if(conta<tb.size())
        {
            if(tb.get(conta).lexema.equals("="))
            {
                conta++;
                if(conta<tb.size())
                {
                    boolean status = isValor();
                    
                    if(conta<tb.size())
                    {
                        if(tb.get(conta).lexema.equals(";"))
                        {
                            conta++;     
                            on=false;
                        }
                        else if(tb.get(conta).token.contains("Oper"))
                        {
                            conta++;
                            Expressoes();
                        }
                        else
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                            listaErro.add(e);
                        }
                    }                
                    else
                    {
                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ; ou ,");
                        listaErro.add(e);
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um valor");
                    listaErro.add(e);
                }
            }
            else if(tb.get(conta).lexema.equals("--")||tb.get(conta).lexema.equals("++"))
            {
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).lexema.equals(";"))
                    {
                        conta++;
                    }
                    else
                    {
                        Expressoes();
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                    listaErro.add(e);
                }
            }
            else if(tb.get(conta).token.contains("seguida"))
            {
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).token.contains("Num")||tb.get(conta).token.contains("Info")||tb.get(conta).token.equals("ID"))
                    {
                        conta++;
                        if(!PontoVirgula())
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                            listaErro.add(e);
                        }
                    }
                    else
                    {
                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou nº");
                        listaErro.add(e);
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou nº");
                    listaErro.add(e);
                }
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ; ou Operador");
            listaErro.add(e);
        }        
    }   
    
    private void        InicializaVariavel2()        {
        if(conta<tb.size())
        {
            if(tb.get(conta).lexema.equals("="))
            {
                conta++;
                if(conta<tb.size())
                {
                    boolean status = isValor();
                    
                    if(conta<tb.size())
                    {
                        if(tb.get(conta).lexema.equals(";"))
                        {
                            conta++;     
                            on=false;
                        }
                        else if(tb.get(conta).token.contains("Oper"))
                        {
                            conta++;
                            Expressoes();
                        }
                        else
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                            listaErro.add(e);
                        }
                    }                
                    else
                    {
                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ; ou ,");
                        listaErro.add(e);
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um valor");
                    listaErro.add(e);
                }
            }
            else if(tb.get(conta).lexema.equals("--")||tb.get(conta).lexema.equals("++"))
            {
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).lexema.equals(";"))
                    {
                        conta++;
                    }
                    else
                    {
                        Expressoes();
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                    listaErro.add(e);
                }
            }
            else if(tb.get(conta).token.contains("seguida"))
            {
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).token.contains("Num")||tb.get(conta).token.contains("Info")||tb.get(conta).token.equals("ID"))
                    {
                        conta++;
                        if(!PontoVirgula())
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ;");
                            listaErro.add(e);
                        }
                    }
                    else
                    {
                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou nº");
                        listaErro.add(e);
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um identificador ou nº");
                    listaErro.add(e);
                }
            }else if(PontoVirgula()){
                conta--;
                on=false;
            }
        }
        else
        {
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava encontrar um ; ou Operador");
            listaErro.add(e);
        }        
    }
    
    private void        IncreDrement()              {
        if(conta<tb.size())
        {
            if(tb.get(conta).token.equals("ID"))
            {
                TbSimbolo.add(tb.get(conta));
                conta++;
                if(conta<tb.size())
                {
                    if(tb.get(conta).token.contains("seguida"))
                    {
                        TbSimbolo.add(tb.get(conta));
                        conta++;
                        if(conta<tb.size())
                        {
                            if(tb.get(conta).token.contains("Info")||tb.get(conta).token.contains("Num")||tb.get(conta).token.equals("ID"))
                            {
                                TbSimbolo.add(tb.get(conta));
                                conta++;
                                if (Virgula()) {
                                    TbSimbolo.add(tb.get(conta-1));
                                    IncreDrement();
                                }
                            }
                        }
                        else
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava um valor");
                            listaErro.add(e);
                        }
                    }
                    else if(tb.get(conta).token.contains("crement"))
                    {
                        TbSimbolo.add(tb.get(conta));
                        conta++;
                        if (Virgula()) {
                            TbSimbolo.add(tb.get(conta-1));
                            IncreDrement();
                        }
                    }
                    else if(tb.get(conta).token.contains("="))
                    {
                        
                        TbSimbolo.add(tb.get(conta));
                        conta++;
                        if(conta<tb.size())
                        {
                            if(tb.get(conta).token.contains("Info")||tb.get(conta).token.contains("Num")||tb.get(conta).token.equals("ID"))//x=12
                            {
                                TbSimbolo.add(tb.get(conta));
                                conta++;
                                if(conta<tb.size())
                                {
                                    if(tb.get(conta).token.equals("OperArt"))//x=12+
                                    {
                                        TbSimbolo.add(tb.get(conta));
                                        conta++;
                                        if(conta<tb.size())
                                        {
                                            if(tb.get(conta).token.contains("Info")||tb.get(conta).token.contains("Num")||tb.get(conta).token.equals("ID"))//x=12+12
                                            {
                                                TbSimbolo.add(tb.get(conta));
                                                conta++;
                                                if (Virgula()) {
                                                    TbSimbolo.add(tb.get(conta-1));
                                                    IncreDrement();
                                                }
                                            }
                                            else
                                            {
                                                Erro e=new Erro(tb.get(conta-1).linha,"Esperava um valor");
                                                listaErro.add(e);
                                            }
                                        }
                                        else
                                        {
                                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava um valor");
                                            listaErro.add(e);
                                        }
                                    }
                                    else
                                    {
                                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava um operador Aritmetrico");
                                        listaErro.add(e);
                                    }
                                }
                                else
                                {
                                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava um operador Aritmetrico");
                                    listaErro.add(e);
                                }
                            }
                            else
                            {
                                Erro e=new Erro(tb.get(conta-1).linha,"Esperava um identificador ou número");
                                listaErro.add(e);
                            }
                        }
                        else
                        {
                            Erro e=new Erro(tb.get(conta-1).linha,"Esperava um identificador ou número");
                            listaErro.add(e);
                        }
                    }
                    else
                    {
                        Erro e=new Erro(tb.get(conta-1).linha,"Esperava um incrmento ou decremento");
                        listaErro.add(e);
                    }
                }
                else
                {
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava um incrmento ou decremento");
                    listaErro.add(e);
                }
            }
            else
            {
                Erro e=new Erro(tb.get(conta-1).linha,"Esperava um identificador");
                listaErro.add(e);
            }
        }
    }    
    private void        IF()                        {
        if(conta<tb.size())
        {
            if(AbrePArenteses1())
            {
                TbSimbolo.add(tb.get(conta-1));
                Expressao3();
                boolean status = FechaPArenteses1();
                if(status){TbSimbolo.add(tb.get(conta-1));}
                AbreChaveta1();
                while(!tb.get(conta).lexema.equals("}")){
                    Bloco();
                }
                FechaChaveta1(); 
                HaIF_ELSE();
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava um (");
                listaErro.add(e);
            }
        }                  
    }
    private void        HaIF_ELSE()                 {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("else"))
            {
                conta++;
                if(conta<tb.size()){
                    if(tb.get(conta).lexema.equals("if")){
                        TbSimbolo.add(tb.get(conta));
                        conta++;
                        IF();
                    }else{
                        ELSE();
                    }
                }else{
                    Erro e=new Erro(tb.get(conta-1).linha,"Esperava a palavra if ou um {");
                    listaErro.add(e);
                }
            }
        }
    }
    private void        ELSE()                      {
        if(conta<tb.size())
        {
            if(AbreChaveta1()){
                while(!tb.get(conta).lexema.equals("}")){
                    Bloco();
                }
                FechaChaveta1();
            }else{
               Erro e=new Erro(tb.get(conta).linha,"Espera um Abrechaveta {");
               listaErro.add(e);
            }
        }else{
            Erro e=new Erro(tb.get(conta-1).linha,"Esperava um (");
            listaErro.add(e);
        }
    }
    private void        WHILE()                     {
        if(conta<tb.size())
        {
            if(AbrePArenteses1())
            {
                TbSimbolo.add(tb.get(conta-1));
                Expressao3();
                boolean status = FechaPArenteses1();
                if(status){TbSimbolo.add(tb.get(conta-1));}
                AbreChaveta1();
                while(!tb.get(conta).lexema.equals("}")){
                    Bloco();
                }
                FechaChaveta1();
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava um (");
                listaErro.add(e);
            }
        }                  
    }
    private void        FOR()                       {
        if(conta<tb.size())
        {
            if(AbrePArenteses1())
            {
                TbSimbolo.add(tb.get(conta-1));
                if(TipoDados())
                {
                    DeclaracaoVariavel();                    
                    Expressao3();
                    if(PontoVirgula())
                    {
                        TbSimbolo.add(tb.get(conta-1));
                        IncreDrement();
                        FechaPArenteses1();
                        TbSimbolo.add(tb.get(conta-1));
                        AbreChaveta1();
                        while(!tb.get(conta).lexema.equals("}")){
                            Bloco();
                        }
                        FechaChaveta1();
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ;");
                        listaErro.add(e);
                    }
                    //PARA INICIAR O FOR COMEÇA COM UMA VARIAVEL E NÃO TIPO DE DADOS
                }else if(isID()){  
                    conta--;
                    DeclaracaoVariavel1();
                    Expressao3();
                    if(PontoVirgula())
                    {
                        Simbolo b=tb.get(conta-1);
                        TbSimbolo.add(b);
                        IncreDrement();
                        FechaPArenteses1();
                        TbSimbolo.add(tb.get(conta-1));
                        AbreChaveta1();
                        while(!tb.get(conta).lexema.equals("}")){
                            Bloco();
                        }
                        FechaChaveta1();
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ;ww");
                        listaErro.add(e);
                    }
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava um tipo de dados");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava um (");
                listaErro.add(e);
            }
        }                  
    }
    private boolean     isWHILE()                   {
        if(conta<tb.size()){
            if(tb.get(conta).lexema.equals("while")){
                conta++;
                return true;
            }
        }
        return false;
    }
    private void        DO()                        {
        if(conta<tb.size())
        {
            if(AbreChaveta1())
            {
                while(!tb.get(conta).lexema.equals("}")){
                    Bloco();
                }
                FechaChaveta1();
                if(isWHILE()){
                    TbSimbolo.add(tb.get(conta-1));
                    boolean status = AbrePArenteses1();
                    if(status){TbSimbolo.add(tb.get(conta-1));}
                    Expressao3();
                    status = FechaPArenteses1();
                    if(status){TbSimbolo.add(tb.get(conta-1));}
                    if(PontoVirgula()){
                        
                    }else{
                        Erro e=new Erro(tb.get(conta).linha,"Esperava um ;");
                        listaErro.add(e);
                    }
                }else{
                    Erro e=new Erro(tb.get(conta).linha,"Esperava a palavra while");
                    listaErro.add(e);
                }
            }else{
                Erro e=new Erro(tb.get(conta).linha,"Esperava um {");
                listaErro.add(e);
            }
        } 
    }
    
}
