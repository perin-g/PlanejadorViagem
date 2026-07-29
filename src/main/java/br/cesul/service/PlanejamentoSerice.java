package br.cesul.service;

import br.cesul.model.Viagem;
import br.cesul.repo.ViagemRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*
* Contém validações e cálculos, isolando a lógica do controller...
* Mas não tendo referência de JavaFX ou MongDB
*
* Essa camada existe para organizar a lógica de negócio da aplicação,
* ou seja, validações e cálculos que fazem sentido para o funcionamento do sistema.
*
* Ela serve para separar responsábilidades e deixar o código mais limpo
*
* Ex:
*   - Impedir datas invertidas
*   - Deixar custo negativo
*   - Evitar datas conflitantes
* Ai sim sua responsabilidade é chamar o repository
*
* Isso evita lógica pesada no controller ou na view
*
* Por que usar service?
*   - Sem ele o controller teria que fazer validações e regras sozinho,
*     gerando código bagunçado, ou então a própria tela chamaria o banco diretamente,
*     quebrando o conceito de MVC
*   - Com ele o Controller apenas chama métodos como adicionar() e recebe o resultado.
*     Toda a lógica fica centralizada e reaproveitável. E também permite testar a lógica
*     de forma isolada sem depender da interface.
* */
public class PlanejamentoSerice {
    // Usa o repository para fazer o CRUD no MongoDB
    private final ViagemRepository repo = new ViagemRepository();

    // Receber os dados da viagem (como se eles viessem da interface gráfica)
    // Adicionar uma nova viagem após chegar regras ou lançar exceção caso o check não seja positivo
    public void adicionar(String destino, LocalDate ini, LocalDate fim, double custo) {
        if (destino == null || destino.isEmpty())
            throw new IllegalArgumentException("Destino vazio");
        if (ini == null || fim ==null)
            throw new IllegalArgumentException("Datas obrigatórias");
        if (custo < 0)
            throw new IllegalArgumentException("Custo negativo");

        if (repo.conflita(ini, fim))
            throw new IllegalArgumentException("Conflita com outra viagem");

        // Como tudo está certo de acordo com minhas regras, devo:
        // Criar um novo objeto Viagem e chamar o repo para salvar no MongoDB
        repo.salvar(new Viagem(null, destino, ini, fim, custo));
    }

    // Métodos de consulta
    // Apenas encapsulta o acesso ao REPO
    // Pode futuramente adicionar regra de negócio
    // Ex: Filtrar por data, agrupar por destino
    public List<Viagem> listar() {
        return repo.listarTodas();
    }

    public double totalGasto() {
        return repo.somarCustos();
    }
}
