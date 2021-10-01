package br.ufmt.compiladores.Analisador_Lexico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LexScanner {

  private char[] conteudo;
  private int estado;
  private int pos;

  public LexScanner(String arq) {
    try {
      byte[] bytes = Files.readAllBytes(Paths.get(arq));
      conteudo = (new String(bytes)).toCharArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

    LexScanner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
  public Token nextToken() {
    if (isEOF()) {
      return null;
    }
    
    estado = 0;
    
    char c;
    
    Token token = null;
    
    String termo = "";
    
    while (true) {
      c = nextChar();
        //System.out.println(c);    
      switch (estado) {
        case 0:
            if (isEspaco(c)){
                estado = 0;
            }
            else if (isLetra(c)){
                estado = 1;
                termo += c;
            }
            else if (isDigito(c)){
                estado = 2;
                termo += c;
            }
            else if ((c == '>') || (c == ':')){
                estado = 4;
                termo += c;
            }
            else if (c == '<'){
                estado = 5;
                termo += c;
            }
            else if ((c == ';') || (c == '+') || (c == '-') || (c == ',')  || (c == '*') || (c == '.') || (c == '/') || (c == '(') || (c == ')') || (c == '$')|| (c == '=')){
                //System.out.println(c);
                estado = 8;
                termo += c;     
            }
            /*else if (c == '.'){
                termo += c;
                token = new Token();
                token.setTipo(Token.simbolo);
                token.setTermo(termo);
                return token; 
            }*/
            else{
                if (c == 0){
                    return null;
                }
                throw new RuntimeException("Caractere não reconhecido!");      
            }
            break;
        case 1:
            if (isLetra(c) || (isDigito(c))){
                estado = 1;
                termo += c;
            }
            else{
                token = new Token();
                token.setTipo(Token.identificador);
                token.setTermo(termo);
                back();
                estado = 0;
                return token; 
            }
            break;
        case 2:
            if (isDigito(c)){
                estado = 2;
                termo += c;
            }
            else if (c == '.'){
                estado = 3;
                termo += c;
            }
            else{
                token = new Token();
                token.setTipo(Token.inteiro);
                token.setTermo(termo);
                back();
                estado = 0;
                return token; 
            }
            break;
        case 3:
            if (isDigito(c)){
                estado = 3;
                termo += c;
            }
            else{
                token = new Token();
                token.setTipo(Token.real);
                token.setTermo(termo);
                back();
                estado = 0;
                return token;
            }
        case 4:
            if (c == '='){
                termo += c;
                token = new Token();
                token.setTipo(Token.simbolo);
                token.setTermo(termo);
                //back();
                estado = 0;
                return token;
            }
            else{
                token = new Token();
                token.setTipo(Token.simbolo);
                token.setTermo(termo);
                back(); 
                estado = 0;
                return token;
            }
        case 5:
            if ((c == '=') || (c == '>')){
                estado = 6;
                termo += c;
            }
            else{
                token = new Token();
                token.setTipo(Token.simbolo);
                token.setTermo(termo);
                back();
                estado = 0;
                return token;
            }
        case 6:
                token = new Token();
                token.setTipo(Token.simbolo);
                token.setTermo(termo);
                back();
                estado = 0;
                return token;
        case 8:
                token = new Token();
                token.setTipo(Token.simbolo);
                token.setTermo(termo);
                back();
                estado = 0;
                return token;
    }
  }
}
private boolean isLetra(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean isDigito(char c) {
      return c >= '0' && c <= '9';
  }

  private boolean isEspaco(char c) {
    return c == ' ' || c == '\n' || c == '\t';
  }

  private boolean isEOF() {
    return pos == conteudo.length;
  }

  private char nextChar() {
    if(isEOF()){
      return 0;
    }
    return conteudo[pos++];
  }

  private void back() {
    if(!isEOF())
      pos--;
  }
}
