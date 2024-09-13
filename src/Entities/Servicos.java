package Entities;

public class Servicos {
	private Integer idservico;
	private String nome;
	private String descricao;
	private Double preco;
	
	public Servicos() {
		
	}
	
	
	public Integer getIdservico() {
		return idservico;
	}


	public void setIdservico(Integer idservico) {
		this.idservico = idservico;
	}


	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
    public String toString() {
        return nome; // Isso garante que o nome do cliente seja exibido na ComboBox
    }
	
}
