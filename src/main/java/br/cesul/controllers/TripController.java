package br.cesul.controllers;


//essa camada de controle cumpre um papel fundamental no padrao mvc
//ela liga a interface grafica que é a view, com a logica de negocio, que é o service(ou model)
// ele vai interagir com os elementos da interface fxml, vai coletar os dados preenchidos pelo usuario
// conversao de tipos como string para double por exemplo, vai tambpme chamar o service que validara os dados da viagem
//e também vai atualizar a interface ( a tabela e o total gasto)

import br.cesul.model.Viagem;
import br.cesul.service.PlanejamentoService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

//controller responde/gerencia eventos da view, converte tipos de dados, e é a ponte entre o botão e
// a camada mais baixa do banco
public class TripController {
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField destinoField;
    @FXML private TextField orcamentoField;
    @FXML private Button btnAdicionar;
    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;
    @FXML private TableView<Viagem> viagensTable;
    @FXML private TableColumn<Viagem, String> colCidade;
    @FXML private TableColumn<Viagem, String> colIni;
    @FXML private TableColumn<Viagem, String> colFim;
    @FXML private TableColumn<Viagem, Number> colCusto;
    @FXML private Label lblTotal;


    private Viagem  v;
    private final PlanejamentoService service = new PlanejamentoService();
    //FORMATADOR DA DATA. SO APLICAR NOS LOCAL DATE LA EMBAIXO
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private void initialize(){
        //oque estiver aq sera executado por primeiro logo apos o FXML ser carregado na tela
        //convem deixar config que vc vai utuilizar durante toda a execucao do codigo
        //como iniciar variaveis, setar o primeiro estado de tela que o usuario
        //deve ver

        //definir como cada coluna extrai e mostra as informacoes da entidade
        colCidade.setCellValueFactory(c->
                new SimpleStringProperty(c.getValue().getDestino()));

        colIni.setCellValueFactory(c->
                new SimpleStringProperty(c.getValue().getDataInicio().format(fmt)));

        colFim.setCellValueFactory(c->
                new SimpleStringProperty(c.getValue().getDataFim().format(fmt)));

        colCusto.setCellValueFactory(c->
                new SimpleDoubleProperty(c.getValue().getCusto()));

        viagensTable.setItems(FXCollections.observableArrayList(service.listar()));

    }

    @FXML public void adicionar() {
        try {


            double custo = Double.parseDouble(orcamentoField.getText().replace(",", "."));

            service.adicionar(destinoField.getText(), dataInicioPicker.getValue(), dataFimPicker.getValue(), custo);
            //pego os itens da tabela e seto de novo, pra "atualizar ela"

            viagensTable.getItems().setAll(service.listar());
            limparCampos();
            atualizarTotal();
        } catch (NumberFormatException e) {
            mostrarErros("Digite apenas números no custo.");

        } catch (Exception e) {
            mostrarErros(e.getMessage());
        }
    }


    private void atualizarTotal() {
        lblTotal.setText("Total: R$ " + String.format("%.f", service.totalGasto()));
    }


    private void limparCampos() {
        destinoField.clear();
        orcamentoField.clear();
        dataInicioPicker.setValue(null);
        dataFimPicker.setValue(null);
    }
    @FXML public void excluirViagem() {
        Viagem v = (Viagem) viagensTable.getSelectionModel().getSelectedItem();

        if (v == null) {
            System.out.println("selecione um viagem para excluir");
        } else {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar Exclusão");
            alerta.setContentText("Você tem certeza que quer excluir essa viagem?");
            alerta.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> opcao = alerta.showAndWait();
            if (opcao.isPresent() && opcao.get() == ButtonType.YES) {
                service.excluir(v);
                viagensTable.getItems().setAll(service.listar());
                Alert aviso = new Alert(Alert.AlertType.INFORMATION);
                aviso.setContentText("viagem excluida com sucesso");
                aviso.showAndWait();
            }
        }
    }

    @FXML public void editarViagem() {
        Viagem temp = (Viagem) viagensTable.getSelectionModel().getSelectedItem();
        if (v == null) {
            if (temp == null) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText("Selecione um viagem para editar");
                alerta.showAndWait();
            }else {
                v = temp;
                destinoField.setText(v.getDestino());
                orcamentoField.setText(String.valueOf(v.getCusto()));
                dataInicioPicker.setValue(v.getDataInicio());
                dataFimPicker.setValue(v.getDataFim());
            }
        } else {
            v.setDestino(destinoField.getText());
            v.setCusto(Double.parseDouble(orcamentoField.getText()));
            v.setDataFim(dataFimPicker.getValue());
            v.setDataInicio(dataInicioPicker.getValue());
            service.editar(v);
            viagensTable.getItems().setAll(service.listar());
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setContentText("Viagem editada com sucesso");
            alerta.showAndWait();
            v = null;
        }
    }


    private void mostrarErros(String  msg){
        new Alert (Alert.AlertType.ERROR);
    }
}


