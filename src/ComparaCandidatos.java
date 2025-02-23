
import java.time.Period;
import java.util.Comparator;

public class ComparaCandidatos implements Comparator<Candidato> {

    @Override
    public int compare(Candidato a, Candidato b) {
        int c = a.getVotos(), d = b.getVotos();
        if (d - c != 0) return d - c;
        return Period.between(b.getNascimento(), a.getNascimento()).getDays();
    }
}