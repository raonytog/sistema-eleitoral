
import java.util.Comparator;

public class ComparaPartidos implements Comparator<Partido>{

    @Override
    public int compare(Partido a, Partido b) {
        return (b.getVotosNominais() + b.getVotosLegenda()) - (a.getVotosNominais() + a.getVotosLegenda());
    }
    
}
