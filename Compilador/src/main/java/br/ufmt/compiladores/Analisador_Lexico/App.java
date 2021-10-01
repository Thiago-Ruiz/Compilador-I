package br.ufmt.compiladores.Analisador_Lexico;

/*

ARQUIVO JAVA PRINCIPAL

 */
public class App {
  public static void main(String[] args) {
    /*LexScanner scan = new LexScanner("input.txt");
    Token token = null;
    do {
      token = scan.nextToken();
      System.out.println(token);
    } while (token != null);*/
    
    
    Analisador_Sintatico  sintatico = new Analisador_Sintatico("input.txt");
    sintatico.Analise();
  }
}
