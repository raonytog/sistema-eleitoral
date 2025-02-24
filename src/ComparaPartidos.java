
import java.util.Comparator;

public class ComparaPartidos implements Comparator<Partido>{

    @Override
    public int compare(Partido a, Partido b) {
        int totalA = a.getVotosNominais() + a.getVotosLegenda(), 
            totalB = b.getVotosNominais() + b.getVotosLegenda();

        if (totalB - totalA != 0) return totalB - totalA;
        return a.getNumero() - b.getNumero();
    }
}
