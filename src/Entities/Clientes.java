package Entities;

public class Clientes {
	private Integer cliente_id;
	private String nome;
	private String email;
	private String telefone;
	private String endereco;
	private String cpf;
	
	public Clientes() {
		
	}
	
	public Integer getCliente_id() {
		return cliente_id;
	}
	
	public void setCliente_id(Integer cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Override
    public String toString() {
        return nome; // Isso garante que o nome do cliente seja exibido na ComboBox
    }

}
