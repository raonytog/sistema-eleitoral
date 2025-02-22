
import java.util.Comparator;

public class ComparaCandidatos implements Comparator<Candidato> {

    @Override
    public int compare(Candidato a, Candidato b) {
        int c = a.getVotos(),
            d = b.getVotos();
        return d - c;
    }

    
}
