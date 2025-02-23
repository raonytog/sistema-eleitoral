import java.time.Period;
import java.util.Comparator;

public class ComparaPartidosPorCandidato implements Comparator<Partido>{

    @Override
    public int compare(Partido a, Partido b) {
        Candidato c = a.getMaisVotado(),
        d = b.getMaisVotado();

        if (c == null && d == null) return 0;
        else if (c == null) return -1;
        else if (d == null) return 1;

        int e = c.getVotos(), f = d.getVotos();
        if (f - e != 0) return f - e;
        return Period.between(d.getNascimento(), c.getNascimento()).getDays();
    }
}
