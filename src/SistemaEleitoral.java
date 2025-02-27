import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class SistemaEleitoral {
    private Map<Integer, Candidato> candidatos = new HashMap<>();
    private Map<Integer, Partido> partidos = new HashMap<>();

    private LocalDate diaVotacao;

    private static int codCargo = 13;
    private final int codMunicipio;

    private int qtdEleitos;
    private int votosLegenda;
    private int votosNominais;
    
    private List<Candidato> eleitos = new LinkedList<>();

    public SistemaEleitoral(int codMunicipio, String pathCandidatos, String diaVotacao) throws Exception {
        
        InputStream is = new FileInputStream(pathCandidatos);
        InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        linha = br.readLine();
        this.codMunicipio = codMunicipio;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse("2018-12-27");
        this.diaVotacao = LocalDate.parse(diaVotacao, formatter);

        while (linha != null) {
            Scanner sc = new Scanner(linha).useDelimiter(";");
            int i;

            String nomeCandidato = "", siglaPartido = "";
            int numeroCandidato = 0, codUE = 0, codCargo = 0, genero = 0, 
            eleito = 0, numeroPartido = 0, numeroFederacao = 0;

            for (i = 0; i < 50; i++) {
                String aux = sc.next();
                
                switch (i) {
                    case 11 -> codUE = Integer.parseInt(aux.substring(1, aux.length() - 1));
                    case 13 -> codCargo = Integer.parseInt(aux);
                    case 16 -> numeroCandidato = Integer.parseInt(aux);
                    case 18 -> nomeCandidato = aux.substring(1, aux.length() - 1);
                    case 25 -> numeroPartido = Integer.parseInt(aux);
                    case 26 -> siglaPartido = aux.substring(1, aux.length() - 1);
                    case 28 -> numeroFederacao = Integer.parseInt(aux);
                    case 36 -> nascimento = LocalDate.parse(aux.substring(1, aux.length() - 1), formatter);
                    case 38 -> genero = Integer.parseInt(aux);
                    case 48 -> eleito = Integer.parseInt(aux);
                }
            }

            if (this.partidos.get(numeroPartido) == null) 
                this.partidos.put(numeroPartido, new Partido(numeroPartido, siglaPartido, numeroFederacao));  

            if (codUE == codMunicipio && codCargo == SistemaEleitoral.codCargo && eleito > -1) {
                Candidato candidato = new Candidato(nomeCandidato, numeroCandidato, this.partidos.get(numeroPartido), nascimento, eleito, genero);
                this.candidatos.put(numeroCandidato, candidato);

                if (eleito == 2 || eleito == 3) { 
                    this.qtdEleitos++; 
                    this.eleitos.add(candidato);
                }
            }
            
            linha = br.readLine();
            sc.close();
        }
        br.close();
    }

    public void contabilizaVotos(String pathVotacao) throws Exception {
        InputStream is = new FileInputStream(pathVotacao);
        InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        linha = br.readLine();
        
        while (linha != null) {
            Scanner sc = new Scanner(linha).useDelimiter(";");
            int i;  

            int numero = 0, codUE = 0, codCargo = 0, qtdVotos = 0; 

            for (i = 0; i < 26; i++) {
                String aux = sc.next();
                
                switch (i) {
                    case 11 -> codUE = Integer.parseInt(aux.substring(1, aux.length() - 1));
                    case 17 -> codCargo = Integer.parseInt(aux);
                    case 19 -> numero = Integer.parseInt(aux);
                    case 21 -> qtdVotos = Integer.parseInt(aux);
                }

                if (i == 13 && codUE != this.codMunicipio) continue;
                if (i == 17 && codCargo != SistemaEleitoral.codCargo) continue;
            }

            if (codUE == this.codMunicipio && codCargo == SistemaEleitoral.codCargo) {
                if (numero <=  99) {
                    Partido partido = this.partidos.get(numero);
                    if (partido != null) { 
                        partido.somaVotosLegenda(qtdVotos);
                        this.votosLegenda += qtdVotos;
                    }

                } else {
                    Candidato candidato = this.candidatos.get(numero);
                    if (candidato != null) {
                        candidato.somaVotos(qtdVotos);
                        this.votosNominais += qtdVotos;
                    }
                }
            }

            linha = br.readLine();
            sc.close();
        }
        br.close();

        for (Entry<Integer, Candidato> c : this.candidatos.entrySet())
            this.partidos.get(c.getValue().getNumeroPartido()).addCandidato(c.getValue());
        
        Collections.sort(this.eleitos, new ComparaCandidatos());
    }

    public List<Partido> ordenaPartidos() {
        List<Partido> lista = new LinkedList<>(this.partidos.values()); 
        Collections.sort(lista, new ComparaPartidos());

        return lista;
    }

    public List<Partido> ordenaPartidosPorCandidato() {
        List<Partido> lista = new LinkedList<>(this.partidos.values());
        Collections.sort(lista, new ComparaPartidosPorCandidato());

        return lista;
    }

    public List<Candidato> ordenaCandidatos() {
        List<Candidato> lista = new LinkedList<>(this.candidatos.values());
        Collections.sort(lista, new ComparaCandidatos());

        return lista;
    }

    public int getQtdEleitos() { return this.qtdEleitos; }

    public List<Candidato> getCandidatosEleitos() {
        return new LinkedList<>(this.eleitos);
    }

    public LocalDate getDiaVotacao() { return this.diaVotacao; }

    public int getVotosLegenda() {
        return votosLegenda;
    }

    public int getVotosNominais() {
        return votosNominais;
    }
}   