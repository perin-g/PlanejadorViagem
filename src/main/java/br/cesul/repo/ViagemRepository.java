package br.cesul.repo;

import br.cesul.config.MongoConfig;
import br.cesul.model.Viagem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViagemRepository {
    // so conversa com o MOngoDB, serve pra atualizar,  editar ou apagar por aqui
    //coneca com a colecao viagens do mongodb
    //usando o mapeamento pojo

    private final MongoCollection<Viagem> col = MongoConfig.db.getCollection( "viagens",Viagem.class );

    public void salvar (Viagem v){
        // insere 1 documento objeto viagem convertido autmaticamente em BSON
        col.insertOne(v);
    }

    public List<Viagem> listarTodas(){
        return col.find() //busca todos os docs
                .sort(Sorts.ascending( "dataInicio")) //ordena por data de inicio
                .into(new ArrayList<>());  //converter o resultado em uma lista java
    }

    public double somaCustos(){
        // 1 - pegar todas as viagens e depois somar o campo custo em memoria
        return col.find() //busca tdo
                .into(new ArrayList<>()) //converte em lista
                .stream() //converte pra lista iteravel
                .mapToDouble(Viagem::getCusto) //extrai o custo em double (os :: sinaliza que sao tds os itens da lista
                .sum(); //dai soma tudo
    }

    //exclui a viagem
    public void excluir(Viagem v){
        col.deleteOne(Filters.eq("_id",v.getId()));


    }

    public void editar (Viagem v){
        col.replaceOne(Filters.eq("_id",v.getId()), v);

    }

    //
    public boolean conflita (LocalDate ini, LocalDate fim) {
        //longo é um int maior, o count documents retorna isos e nao int
        long qtd = col.countDocuments(Filters.and(
                //campo menor ou igual a valor
                // a viagem que eu estiver iterando do banco nao pode comecar antes ou durante o intervalo dado
                Filters.lte("dataInicio", fim),
                Filters.gte("dataFim", ini)
        ));

        return qtd > 0;
    }


}
