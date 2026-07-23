package br.cesul.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

/*
* Classe utilitária SINGLETON que expõe um link para as MongoDatabases que utilizaremos no projeto
*
* Mantém uma única conexão/sessão com o banco durante a execução do aplicativo
* Faremos também o suporte para mapear POJO's automaticamente
*
* Não faz sentido que criemos Objetos de MongoConfig, por quê?
* Porque MongoConfig é um conjunto de configurações de sessão que farei apenas na inicialização
* do projeto e utilizarei seus artefatos até o final da execução.
* */
public final class MongoConfig {
    // URL padrão que utilizaremos para criar a sessão
    private static final String URI = "mongodb://localhost:27017";

    // Cliente (que será compartilhado) do MongoDB
    private static final MongoClient client;

    // Acesso e exposição ao banco 'trippllanner'
    public static final MongoDatabase db;

    // Bloco estático de inicialização (executa só uma vez)
    static {
        // Faremos o mapeamento de POJO's: Plain Old Java Objet
        // 1 - Criar um 'codec provier' que sabe converter POJO's
        //      para documentos BSON automaticamente (e o contrário também)
        PojoCodecProvider pojoCodec = PojoCodecProvider.builder().automatic(true).build();
        //.automatic(true) ativa o mapeamento automático (verificar getters/setters, construtores)

        // 2 - Criar um 'codec registry' que é uma lista de codecs que o MongoDB vai usar.
        //      Primeiro inclui o codec padrão do MongoDB (Serve para tipos comuns como String, Integer...
        //      E depois inclui o codec para POJO's que criamos acima)
        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),// codecs básicos
                CodecRegistries.fromProviders(pojoCodec)
        )).applyConnectionString(new ConnectionString(URI)).build();

        // Cria o cliente MongoDB com as configurações personalizadas de serialização
        client = MongoClients.create(settings);

        // Obter a referência do banco de dados
        db = client.getDatabase("tripplanner");
    }

    /*
    * O que podemos fazer com isso?
    * Em qualquer lugar do código você pode fazer:
    * MongoCollection<Viagem> viagens = MongoConfig.db.getCollection("viagens", Viagem.class)
    * Portanto você pode fazer:
    * viagens.insertOne(new Viagem(...));
    * Tudo sem precisar serializar manualmente para/from Document.
    *
    * Se quiser lmitar essa conversão automática a algum pacote específico
    * Basta trocar: PojoCodecProvider.builder().automatic(true)
    * Por:
    * PojoCodecProvider.builder().register("br.cesul.model")
    * Assim ele só tenta mapear classes dentro deste pacote
    * */
    
    // Impedir que a classe seja instanciada (é uma classe utilitária)
    private MongoConfig(){}
}
