package br.cesul.model;


import org.bson.types.ObjectId;

import java.time.LocalDate;

//espelha identidade do banco
//cada model delimita as entidades que utilizaremos no projeto
//espelha bidirecionalmente a estrutura do arquivo no mongo e no java
//necessita de : construtor sem argumentos e getter e setter e publicos (por exigencia do codec do mongo)
public class Viagem {
    private ObjectId id; //será gerado pelo mongo
    private String destino;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double custo;

    //pq ele exige esse construtor? o POJO codec do mongo utiliza reflexão para instânciar objetos. Para isso,
    // ele precisa de um construtor padrão vazio para poder criar o objeto, antes de preencher seus campos
    // com os dados do banco, dessa forma ele faz isso.
    public Viagem() {
    }


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

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }
}
