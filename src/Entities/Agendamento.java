package Entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class Agendamento implements Serializable{
	
	private Integer idagendamento;
    private Timestamp dataHora;
    private Integer idcliente;
    private Integer idservico;
    private Integer idbarbeiro;
    
    public Agendamento() {
    	
    }
    
	public Agendamento(Integer idagendamento, Timestamp dataHora, Integer idcliente, Integer idservico, Integer idbarbeiro) {
		this.idagendamento = idagendamento;
		this.dataHora = dataHora;
		this.idcliente = idcliente;
		this.idservico = idservico;
		this.idbarbeiro = idbarbeiro;
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
