package br.cesul.service;


//aqui dentro temos validações e cálculos isolando a lógica do controller, mas não tendo referência de javafx ou Mongo
// essa camada existe para organizar a lógica de negócio da aplicação, ou seja, validações e cálculos, que fazem sentido
// para o seu negócio

//ela serve pra separar responsabilidades e deixar o código mais limpo
//ex de uso
// -impedir datas invertidas
// -deixar destino em branco
// -deixar custo negativo
// -evitar datas conflitantes

// se no momento de salvar a viagem, todas as regras estiverem corretas ai sim sua responsabilidade é chamar o repository
//pra realizar a continuação do processo de  salvamento
// isso evita lógica pesada no controller ou na view

//pq usar service : sem ele, o controller teria que fazer validacoes e regras sozinho, gerando codigo bagunçado,
//ou ent a própria tela chamaria o banco diretamente quebrando o conceito de MVC
// com ele, o controller apenas chama métodos  e recebe o resultado
//toda a logica fica centralizada e reaproveitavel, alem disso, também te permite testar a lógica de forma isolada
// sem depender da interface

import br.cesul.model.Viagem;
import br.cesul.repo.ViagemRepository;

import java.time.LocalDate;
import java.util.List;

public class PlanejamentoService {
    //o service usa o repository pra CRUD no mongo
    private final ViagemRepository repo = new ViagemRepository();

    //receber os dados da viagem (como se eles viessem da interface gráfica)
    //adicionar uma viagem após checar regras ou lancar excessao caso o check não seja positivo
    public void adicionar (String destino, LocalDate ini, LocalDate fim, double custo) {

        if(destino == null || destino.isEmpty())
            throw new IllegalArgumentException("Destino vazio");
        if(ini == null || fim == null)
            throw new IllegalArgumentException("Datas obrigatorias");
        if (custo < 0)
            throw new IllegalArgumentException("Custo negativo");
        if (repo.conflita(ini, fim))
            throw new IllegalArgumentException("Conflita com outra viagem");

        //como tudo ta certo de acordo com as minhas regras agr podemos
        //criar novo obj viagem e chamar o repo pra salvar no mongo
        repo.salvar(new Viagem(null, destino, ini, fim, custo));

    }

    //Metodos de consulta

    public List<Viagem> listar() {
        return repo.listarTodas();
    }

    public double totalGasto() {
        return repo.somaCustos();
    }

    public void excluir(Viagem v) {
        repo.excluir(v);
    }
    public void editar(Viagem v) {
        repo.editar(v);
    }

}
