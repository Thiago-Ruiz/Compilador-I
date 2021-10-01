package br.ufmt.compiladores.Analisador_Lexico;

import java.util.HashMap;
import java.util.Map;


public class Analisador_Sintatico {
    
    private LexScanner scan;    
    private String simbolo;
    private int tipo;
    
    private Map<String, Simbolo> tabelaSimbolo = new HashMap();
    
    public Analisador_Sintatico (String arq){
        scan = new LexScanner(arq);
    }
    
    public void Analise(){
        getToken();
        programa();
        
        if (simbolo.equals("")){
            System.out.println("Tudo certo!!");
        }
        else{
            throw new RuntimeException("Erro sintático! Esperava-se fim de cadeia");
        }
    
    }
    
    
    private void getToken(){
        Token token = scan.nextToken();
        simbolo ="";
        if(token != null){
            simbolo = token.getTermo();
            System.out.println(simbolo);
            tipo = token.getTipo();
        }

    }
    
    
    private void programa(){
        if (simbolo.equals("program")){ 
            
            getToken();
            if (tipo == Token.identificador){
                
                getToken();
                corpo();
                if (simbolo.equals(".")){
                    
                    getToken();
                }
                else{
                    throw new RuntimeException("Esperava '.', encontrou: " + simbolo);
                }
            }
        }
        else{
            throw new RuntimeException("Esperava 'program', encontrou: " + simbolo);
        }
    }
        
    
    private void corpo(){
        dc();
        if (simbolo.equals("begin")){
            getToken();
            comandos();
            if (simbolo.equals("end")){
                getToken();
            }
            else{
                throw new RuntimeException("Esperava 'end', encontrou: " + simbolo);
            }
        }
        else{
            throw new RuntimeException("Esperava 'begin', encontrou: " + simbolo);
        }
    }
    
    private void dc(){
        if ((simbolo.equals("real")) || (simbolo.equals("integer"))){
            dc_v();

            mais_dc();

        }
        
    }
    
    private void mais_dc(){
        if (simbolo.equals(";")){
            getToken();
            dc();
        }
    }
    
    private void dc_v(){
        tipo_var();
        if (simbolo.equals(":")){
            getToken();
            variaveis();
        }
        else{
            throw new RuntimeException("Esperava ':', encontrou: " + simbolo);
        }
    
    }
    
    private void tipo_var(){
        if (simbolo.equals("real") || (simbolo.equals("integer"))){
            getToken();
        }
        else{
            throw new RuntimeException("Esperava 'real'ou 'integer', encontrou: " + simbolo);
        }
    }
    
    private void variaveis(){
        
        if (tipo != Token.identificador){
            throw new RuntimeException("Erro Sintático!! Esperava um identificador");
        }
        
        if (tabelaSimbolo.containsKey(simbolo)){
            throw new RuntimeException("Erro Semântico!! identificador já encontrado"+ simbolo);
        }
        else{
            tabelaSimbolo.put(simbolo, new Simbolo(this.tipo, simbolo));
        }
        
        getToken();
        mais_var();  
    }
    
    private void mais_var(){
        if (simbolo.equals(",")){
            getToken();
            variaveis();
        }
    }
    
    private void comandos(){
        comando();
        mais_comandos();
    }
    
    private void mais_comandos(){
        if (simbolo.equals(";")){
            getToken();
            comandos();
        }
    }
    
    private void comando(){
        if (simbolo.equals("read")){
            getToken();
            if (simbolo.equals("(")){
                getToken();
                if (tipo == Token.identificador){
                    if (tabelaSimbolo.containsKey(simbolo)){
                        getToken();
                        if (simbolo.equals(")")){
                            getToken();
                        }
                        else{
                            throw new RuntimeException("Esperava ')', encontrou: " + simbolo);
                        }
                    }
                    else{
                        throw new RuntimeException("Erro Semântico!! Identificador não declarado");
                    }
                }
                else{
                    throw new RuntimeException("Esperava tipo identificador - 0 : " + tipo);
                }
            }
            else{
                throw new RuntimeException("Esperava '(', encontrou: " + simbolo);
            }
        }
        else if (simbolo.equals("write")){
            getToken();
            if (simbolo.equals("(")){
                getToken();
                if (tipo == Token.identificador){
                    if (tabelaSimbolo.containsKey(simbolo)){
                        getToken();
                        if (simbolo.equals(")")){
                            getToken();
                        }
                        else{
                            throw new RuntimeException("Esperava ')', encontrou: " + simbolo);
                        }
                    }
                    else{
                        throw new RuntimeException("Erro Semântico!! Identificador não declarado");
                    }
                }
                else{
                    throw new RuntimeException("Esperava tipo identificador - 0 : " + tipo);
                }
            }
            else{
                throw new RuntimeException("Esperava '(', encontrou: " + simbolo);
            }
        }
        else if (tipo == Token.identificador){
            if (tabelaSimbolo.containsKey(simbolo)){
                getToken();
                if (simbolo.equals(":=")){
                    getToken();
                    expressao();    
                }
                else{
                   throw new RuntimeException("Esperava ':=', encontrou: " + simbolo); 
                }
            }else{
                throw new RuntimeException("Erro Semântico!! Identificador não declarado");
            }
        }
        else if (simbolo.equals("if")){
            getToken();
            condicao();
            if (simbolo.equals("then")){
                getToken();
                comandos();
                pfalsa();
                if (simbolo.equals("$")){
                    getToken();
                }
            }
            else{
                throw new RuntimeException("Esperava 'then', encontrou: " + simbolo);
            }
        }
        else{
            throw new RuntimeException("Esperava 'if', encontrou: " + simbolo);
        }
    }
    
    private void condicao(){
        expressao();

        relacao();

        expressao();
    }
    
    private void relacao(){
        if (simbolo.equals("=")){
            getToken(); 
        }
        else if (simbolo.equals("<>")){
            getToken();
        }
        else if (simbolo.equals(">=")){
            getToken();
        }
        else if (simbolo.equals(">")){
            getToken();
        }
        else if (simbolo.equals("<")){
            getToken();
        }
        else{
            throw new RuntimeException("Esperava '=' || '<>' || '>=' || '>' || '<', encontrou: " + simbolo);
        }
    }
    
    private void expressao(){
        termo();

        outros_termos();
    }
    
    private void termo(){
        op_un();

        fator();

        mais_fatores();
    }
    
    private void op_un(){
        if (simbolo.equals("-")){
            getToken();
        }
    }
    
    private void fator(){
        if (tipo == Token.identificador){
            if (tabelaSimbolo.containsKey(simbolo)){
                getToken();
            } 
            else{
                throw new RuntimeException("Erro Semântico!! Identificador não declarado");
            }   
        }
        else if (tipo == Token.inteiro){
            getToken();
        }
        else if (tipo == Token.real){
            getToken();
        }
        else if (simbolo.equals("(")){
            getToken();
            expressao();
            if (simbolo.equals(")")){
                getToken();
            }
            else{
                throw new RuntimeException("Esperava ')', encontrou: " + simbolo);
            }
        } 
        else{
            throw new RuntimeException("Esperava '(', encontrou: " + simbolo);
        }
    }
    
    private void outros_termos(){
        if ((simbolo.equals("+")) || (simbolo.equals("-"))){
            //getToken();
            op_ad();

            termo();

            outros_termos();
        }
    }
    
    private void op_ad(){
        if ((simbolo.equals("+")) || (simbolo.equals("-"))){
            getToken();
        }
        else{
            throw new RuntimeException("Esperava '+' || '-', encontrou: " + simbolo);
        }
    }
    
    private void mais_fatores(){
        if (simbolo.equals("*") || (simbolo.equals("/"))){
            //getToken();

            op_mul();

            fator();

            mais_fatores();
        }
    }
    
    private void op_mul(){
        if (simbolo.equals("*") || (simbolo.equals("/"))){
            getToken();
        }
        else{
            throw new RuntimeException("Esperava '*' ou '/', encontrou: " + simbolo);
        }
    }
    
    private void pfalsa(){
        if (simbolo.equals("else")){
            getToken();
            comandos();
        }
    }
}
