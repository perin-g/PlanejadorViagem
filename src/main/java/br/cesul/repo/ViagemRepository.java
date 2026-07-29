package br.cesul.repo;

import br.cesul.config.MongoConfig;
import br.cesul.model.Viagem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
* Camada de persistência. So fala com o MongoDB e devolve/recebe objetos do modelo.
* Aqui não haverá import de JavaFX, nem qualquer operação com oura entidade que não a Viagem
* */
public class ViagemRepository {
    // Conecta com a coleção viagens no MongoDB usando o mapeamento POJO
    private final MongoCollection<Viagem> col = MongoConfig.db.getCollection("viagens", Viagem.class);

    public void salvar(Viagem v) {
        // Insere um docmento (Objeto Viagem convertido automaticamente em BSON)
        col.insertOne(v);
    }

    public List<Viagem> listarTodas() {
        return col.find() // Busca todos os documentos
                .sort(Sorts.ascending("dataInicio")) // Ordena por data de início
                .into(new ArrayList<>()); // Converter/cast em uma lista Java
    }

    public double somarCustos() {
        /*
        * 1 - Pegar todas as viagens
        * 2 - somar o campo 'custo' em memória
        * */
        return col.find().into(new ArrayList<>()).stream().mapToDouble((Viagem::getCusto)).sum();
    }

    // Verificar se [inicio, fim] se sobrepões a alguma viagem existente
    public boolean conflita(LocalDate ini, LocalDate fim) {
        long qtd = col.countDocuments(Filters.and(
                // Campo menor ou igual a valor
                // A viagem que eu estiver iterando no banco não pode começar antes ou durante o intervalo dado
                Filters.lte("dataInicio", fim),
                Filters.gte("dataFim", ini)
        ));

        return qtd > 0;
    }
}
