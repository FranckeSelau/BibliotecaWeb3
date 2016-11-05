/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorio;

/**
 *
 * @author lhries
 */
public class LivroMaisRetirado {
    private String nome;
    private Long qtdeTelefones;
    
    public LivroMaisRetirado(String nome, Long qtdeTelefones){
        this.nome = nome;
        this.qtdeTelefones =qtdeTelefones;
        
    }

    public String getNome() {
        return nome;
    }

    public Long getQtdeTelefones() {
        return qtdeTelefones;
    }
    
    
}
