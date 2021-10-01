package br.ufmt.compiladores.Analisador_Lexico;


public class Simbolo {
    private String nome;
    private int tipo;
    
    
    // Método Construtor
    public Simbolo(int tipo, String nome){ 
        this.nome = nome;
        this.tipo = tipo;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public int getTipo(){
        return this.tipo;
    }
    
    public void setTipo(String nome){
        this.tipo = tipo;
    }
}
