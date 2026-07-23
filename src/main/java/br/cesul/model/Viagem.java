package br.cesul.model;

import jdk.vm.ci.meta.Local;
import org.bson.types.ObjectId;

import java.time.LocalDate;

/*
* Camada model delimita as entidades que utilizaremos no projeto
* Espelha bidirecionalmente a estrutura do arquivo no MongoDB e no Java
* Necessita de
*   - Construtor sem argumentos
*   - Getters e Setters públicos,
* Por exigência do codec do MongoDB
* */
public class Viagem {
    private ObjectId id; //Será gerado pelo MongoDB
    private String destino;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double custo;

    /*
    * O POJO Codec do MongoDB utiliza reflexão para instanciar objetos
    * Para isso, ele precisa de um construtor padrão (vazio)
    * para poder criar o objeto antes de preencher seus campos com os dados do banco
    * */
    public Viagem() {}

    public Viagem(ObjectId id, String destino, LocalDate ini, LocalDate fim, double custo) {
        this.id = id;
        this.destino = destino;
        this.dataInicio = ini;
        this.dataFim = fim;
        this.custo = custo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }
}
