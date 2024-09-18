package Dto;

import java.sql.Timestamp;

public class AgendamentoRequestDto {
	
	private Integer idagendamento;
	private Timestamp dataHora;
	private String clienteNome;
	private String servicoNome;
	private String barbeiroNome;
	private Double servicoPreco;
	
	public AgendamentoRequestDto() {
		
	}
	
	public AgendamentoRequestDto(Integer idagendamento, Timestamp dataHora, String clienteNome, String servicoNome, String barbeiroNome,Double servicoPreco) {
		
		this.idagendamento = idagendamento;
		this.dataHora = dataHora;
		this.clienteNome = clienteNome;
		this.servicoNome = servicoNome;
		this.barbeiroNome = barbeiroNome;
		this.servicoPreco = servicoPreco;
	}
	
	

	public Integer getIdagendamento() {
		return idagendamento;
	}

	public void setIdagendamento(Integer idagendamento) {
		this.idagendamento = idagendamento;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getServicoNome() {
		return servicoNome;
	}

	public void setServicoNome(String servicoNome) {
		this.servicoNome = servicoNome;
	}

	public String getBarbeiroNome() {
		return barbeiroNome;
	}

	public void setBarbeiroNome(String barbeiroNome) {
		this.barbeiroNome = barbeiroNome;
	}

	public Double getServicoPreco() {
		return servicoPreco;
	}

	public void setServicoPreco(Double servicoPreco) {
		this.servicoPreco = servicoPreco;
	}
	
	
}
