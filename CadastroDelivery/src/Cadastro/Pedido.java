package Cadastro;

//Orientação de Objetos para Cadastro dos Pedidos/ Produtos no app de Delivery
// Parte feita por João Lucas Carreira

public class Pedido {
	private String codigo;
    private String nome;
    private String categoria;
    private double preco;
    
    public Pedido (String codigo, String nome, String categoria, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { 
    	this.codigo = codigo; 
    	}

    public String getNome() { return nome; }
    public void setNome(String nome) { 
    	this.nome = nome; 
    	}

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) {
    	this.categoria = categoria; 
    	}

    public double getPreco() { return preco; }
    public void setPreco(double preco) {
    	this.preco = preco;
    	}

    public String toCSV() {
        return codigo + ";" + nome + ";" + categoria + ";" + preco;
    }


}
