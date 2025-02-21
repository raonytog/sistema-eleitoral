import java.util.Date;

public class Candidato {
    private String  nome;
    private int     numero;
    private Partido partido;

    private int     votos;

    private Date    data;

    private int     genero;
    private boolean eleito;
    
    public Candidato(String nome, int numero, Partido partido, Date data, boolean eleito, int genero) {
        this.nome = nome;
        this.numero = numero;
        this.partido = partido;
        this.data = data;
        this.eleito = eleito;
        this.genero = genero;
    }
}

