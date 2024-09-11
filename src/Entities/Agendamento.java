package Entities;

import java.sql.Timestamp;

public class Agendamento {
	
	private Integer idAgenda;
    private Timestamp dataHora;
    private Integer idcliente;
    private Integer idservico;
    private Integer idbarbeiro;
    
	public Agendamento(Integer idAgenda, Timestamp dataHora, Integer idcliente, Integer idservico, Integer idbarbeiro) {
		super();
		this.idAgenda = idAgenda;
		this.dataHora = dataHora;
		this.idcliente = idcliente;
		this.idservico = idservico;
		this.idbarbeiro = idbarbeiro;
	}

	public Integer getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(Integer idAgenda) {
		this.idAgenda = idAgenda;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}

	public Integer getIdservico() {
		return idservico;
	}

	public void setIdservico(Integer idservico) {
		this.idservico = idservico;
	}

	public Integer getIdbarbeiro() {
		return idbarbeiro;
	}

	public void setIdbarbeiro(Integer idbarbeiro) {
		this.idbarbeiro = idbarbeiro;
	}

    

}
