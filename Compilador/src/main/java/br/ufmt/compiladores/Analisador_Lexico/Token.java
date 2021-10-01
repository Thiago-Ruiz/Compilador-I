package br.ufmt.compiladores.Analisador_Lexico;

public class Token {

  public static final int identificador = 0;
  public static final int inteiro = 1;
  public static final int real = 2;
  public static final int simbolo = 3;
  
  private int tipo;
  private String termo;

  public int getTipo() {
    return this.tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  public String getTermo() {
    return this.termo;
  }

  public void setTermo(String termo) {
    this.termo = termo;
  }

  @Override
  public String toString() {
    return "Token [" + tipo +", " + termo+"]";
  }
}
