package br.cesul.controllers;

import br.cesul.model.Viagem;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/*
* Esta camada de controller cumpre um papel fundamental no padrão MCV:
* Ligar a interface gráfica (view) com a lógica de negócio (service)
*
* - Interage com os elementos da interface fxml
* - Coletar os dados preenchidos pelo usuário na tela
* - Conversão de tipos como String para double
* - Chamar o Service que validará a viagem
* - Atualizar a interface (tabela e total gasto)
*
* Em resumo: Controller responde a EVENTOS da view
* Converte tipos de dados, e é a ponte entre o botão e a camada mais baixa do banco
* */
public class TripController {
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField destinoField;
    @FXML private TextField orcamentoField;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Viagem> viagensTable;
    @FXML private TableColumn<Viagem, String> colCidade;
        @FXML private TableColumn<Viagem, String> colIni;
    @FXML private TableColumn<Viagem, String> colFim;
    @FXML private TableColumn<Viagem, Number> colCusto;
    @FXML private Label lblTotal;

    @FXML public void adicionar() {}
}
