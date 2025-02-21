
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class SistemaEleitoral {
    Map<Integer, Candidato> candidatos = new HashMap<>();
    Map<Integer, Partido> partidos = new HashMap<>();

    List<Candidato> eleitos = new LinkedList<>();
    List<Candidato> candidatosMaisVotados;
    List<Partido> partidosMaisVotados;


}
