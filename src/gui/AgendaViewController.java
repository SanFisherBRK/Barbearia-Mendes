package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import BD.Conexao;
import Dto.AgendamentoRequestDto;
import Entities.Agendamento;
import Entities.Barbeiros;
import Entities.Clientes;
import Entities.Servicos;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AgendaViewController implements Initializable {
    
    @FXML
    private TableView<AgendamentoRequestDto> tbv_agenda;
    
    @FXML
    private DatePicker dtp_datahora;

    @FXML
    private ComboBox<Clientes> cbb_clientes;
    
    @FXML
    private ComboBox<Servicos> cbb_servico;
    
    @FXML
    private ComboBox<Barbeiros> cbb_barbeiro;

    @FXML
    private TableColumn<AgendamentoRequestDto, String> tbc_barbeiro;

    @FXML
    private TableColumn<AgendamentoRequestDto, String> tbc_cliente;

    @FXML
    private TableColumn<AgendamentoRequestDto, Timestamp> tbc_datahora;

    @FXML
    private TableColumn<AgendamentoRequestDto, Integer> tbc_id;

    @FXML
    private TableColumn<AgendamentoRequestDto, String> tbc_servico;
    
    @FXML
    private TableColumn<AgendamentoRequestDto, Double> tbc_preco;

    @FXML
    private Button btn_agendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	tbv_agenda.setItems(FXCollections.observableArrayList(buscaTodos()));
        cbb_clientes.setItems(obterNomesClientes());
        cbb_barbeiro.setItems(obterNomesBarbeiros());
        cbb_servico.setItems(obterNomesServicos());
        
    }


	@FXML
    public void onAgendarServico() throws SQLException {
        Agendamento agenda = new Agendamento();
        
     // Faz a verificação para ver se todos os campos foram preenchidos
        if (dtp_datahora.getValue() == null || cbb_clientes.getValue() == null || cbb_servico.getValue() == null || cbb_barbeiro.getValue() == null) {
            Alerts.showAlert("Agendar Serviço", "Preencha todos os campos", "Tentar de novo", AlertType.INFORMATION);
            return;
        }
        
        agenda.setDataHora(Timestamp.valueOf(dtp_datahora.getValue().atStartOfDay()));
        agenda.setIdcliente(cbb_clientes.getValue().getCliente_id());
        agenda.setIdservico(cbb_servico.getValue().getIdservico());
        agenda.setIdbarbeiro(cbb_barbeiro.getValue().getIdbarbeiro());

        // Cria a query de inserção dos dados na tabela do banco de dados.
        String sqlCheckCadastro = "SELECT COUNT(*) FROM agendamento WHERE datahora = ?";
        String sql = "INSERT INTO agendamento (datahora, cliente_id, servico_id, barbeiro_id) VALUES(?, ?, ?, ?)";

        // Cria a conexão com o banco de dados dentro de um tratamento de erro
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement checarCadastro = conn.prepareStatement(sqlCheckCadastro);
             PreparedStatement enviaParaOBanco = conn.prepareStatement(sql)) {

            checarCadastro.setTimestamp(1, agenda.getDataHora());
            ResultSet result = checarCadastro.executeQuery();
            result.next();

                int count = result.getInt(1);

                if (count > 0) {
                	
                    Alerts.showAlert("Agendar", "Agendamento já existe", "ok", AlertType.INFORMATION);
                    
                } else {
                	
                    // Envia os dados digitados para a tabela agendamento no banco de dados barbearia
                    enviaParaOBanco.setTimestamp(1, agenda.getDataHora());
                    enviaParaOBanco.setInt(2, agenda.getIdcliente());
                    enviaParaOBanco.setInt(3, agenda.getIdservico());
                    enviaParaOBanco.setInt(4, agenda.getIdbarbeiro());

                    enviaParaOBanco.executeUpdate();

                    Alerts.showAlert("Cadastrar Cliente", "Cadastro realizado com sucesso", "ok", AlertType.INFORMATION);
                    buscaTodos();
                }
            }

         catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    @FXML
    public ObservableList<AgendamentoRequestDto> buscaTodos() {
        
        // Configurando as células da tabela
    	tbc_id.setCellValueFactory(new PropertyValueFactory<>("idagendamento"));
        tbc_datahora.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        tbc_cliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        tbc_servico.setCellValueFactory(new PropertyValueFactory<>("servicoNome"));
        tbc_barbeiro.setCellValueFactory(new PropertyValueFactory<>("barbeiroNome"));
        tbc_preco.setCellValueFactory(new PropertyValueFactory<>("servicoPreco"));
        
        ObservableList<AgendamentoRequestDto> agendamentos = FXCollections.observableArrayList();
        String sql = "SELECT cliente.nome AS NomeDoCliente, " +
                     "servico.nome AS NomeDoServico, " +
                     "agendamento.idagendamento AS IdDoAgendamento," +
                     "barbeiro.nome AS NomeDoBarbeiro, " +
                     "agendamento.dataHora AS DataDoAgendamento, " +
                     "servico.preco AS PrecoDoServico " +
                     "FROM agendamento " +
                     "JOIN cliente ON agendamento.Cliente_id = cliente.idCliente " +
                     "JOIN servico ON agendamento.Servico_id = servico.idServico " +
                     "JOIN barbeiro ON agendamento.barbeiro_id = barbeiro.idBarbeiro";
        
        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                AgendamentoRequestDto agenda = new AgendamentoRequestDto();
                
                // Assumindo que você tem esses métodos setters
                agenda.setIdagendamento(rs.getInt("IdDoAgendamento"));
                agenda.setDataHora(rs.getTimestamp("DataDoAgendamento"));
                agenda.setClienteNome(rs.getString("NomeDoCliente"));
                agenda.setServicoNome(rs.getString("NomeDoServico"));
                agenda.setBarbeiroNome(rs.getString("NomeDoBarbeiro"));
                agenda.setServicoPreco(rs.getDouble("PrecoDoServico"));

                agendamentos.add(agenda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agendamentos;
    }


    
    public ObservableList<Clientes> obterNomesClientes() {
        ObservableList<Clientes> clientes = FXCollections.observableArrayList();
        String sql = "SELECT idcliente, nome FROM cliente";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setCliente_id(rs.getInt("idcliente"));
                cliente.setNome(rs.getString("nome"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public ObservableList<Barbeiros> obterNomesBarbeiros() {
        ObservableList<Barbeiros> barbeiros = FXCollections.observableArrayList();
        String sql = "SELECT idbarbeiro, nome FROM barbeiro";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
                Barbeiros barbeiro = new Barbeiros();
                barbeiro.setIdbarbeiro(rs.getInt("idbarbeiro"));
                barbeiro.setNome(rs.getString("nome"));
                barbeiros.add(barbeiro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barbeiros;
    }

    public ObservableList<Servicos> obterNomesServicos() {
        ObservableList<Servicos> servicos = FXCollections.observableArrayList();
        String sql = "SELECT idservico, nome FROM servico";

        try (Connection conn = new Conexao().getConnection();
             PreparedStatement buscar = conn.prepareStatement(sql);
             ResultSet rs = buscar.executeQuery()) {

            while (rs.next()) {
            	Servicos servico = new Servicos();
            	servico.setIdservico(rs.getInt("idservico"));
            	servico.setNome(rs.getString("nome"));
            	servicos.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicos;
    }

    
}
