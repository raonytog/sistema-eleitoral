import java.io.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
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
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
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

    public void imprimeNumeroDeVagas() {
        System.out.println("Número de vagas: " + this.qtdEleitos);
    }

    public void imprimeEleitos() {
        System.out.println("Vereadores eleitos:");

        int i = 1;
        for (Candidato candidato : this.eleitos) {
            if (i > this.qtdEleitos) break;
            System.out.print(i + " - ");
            System.out.println(candidato);
            i++;
        }
    }

    public void imprimeMaisVotados() {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        List<Candidato> maisVotados = this.ordenaCandidatos();
        int i = 1;
        for (Candidato candidato : maisVotados) {
            if (i > this.qtdEleitos) break;
            System.out.print(i + " - ");
            System.out.println(candidato);
            i++;
        }
    }

    public void imprimeSeriamEleitos() {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");

        List<Candidato> maisVotados = this.ordenaCandidatos();
        int i = 1;
        for (Candidato candidato : maisVotados) {
            if (i > this.qtdEleitos) break;

            if (candidato.getEleito() != 2 && candidato.getEleito() != 3) {
                System.out.print(i + " - ");
                System.out.println(candidato);
            }
            i++;
        }
    }

    public void imprimeEleitosBeneficiados() {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");

        List<Candidato> maisVotados = this.ordenaCandidatos();
        int i = 1;
        for (Candidato candidato : maisVotados) {
            if (i > this.qtdEleitos && (candidato.getEleito() == 2 || candidato.getEleito() == 3)) {
                System.out.print(i + " - ");
                System.out.println(candidato);
            }
            i++;
        }
    }

    public void imprimePartidosMaisVotados() {
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        List<Partido> maisVotados = this.ordenaPartidos();
        int i = 1;
        for (Partido partido : maisVotados) {
            String out = i + " - " + partido + ", " + brFormat.format(partido.getVotosTotais()) + " votos ";
            out += "(" + brFormat.format(partido.getVotosNominais()) + " nominais e "; 
            out += brFormat.format(partido.getVotosLegenda()) + " de legenda), ";
            out += partido.getTotalEleitos();

            if (partido.getTotalEleitos() > 0) out += " candidatos eleitos";
            else out += " candidato eleito";

            System.out.println(out);
            i++;
        }
    }

    public void imprimeExtremosDosPartidos() {
        System.out.println("Primeiro e último colocados de cada partido:");

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        List<Partido> maisVotados = this.ordenaPartidosPorCandidato();
        int i = 1;
        for (Partido partido : maisVotados) {
            if (partido.getMaisVotado() == null || partido.getMenosVotado() == null || partido.getVotosTotais() == 0) continue;

            String out = i + " - " + partido + ", ";
            out += partido.getMaisVotado().getNome() + " (" + partido.getMaisVotado().getNumero() + ", ";
            out += brFormat.format(partido.getMaisVotado().getVotos()) + " votos) / ";
            out += partido.getMenosVotado().getNome() + " (" + partido.getMenosVotado().getNumero() + ", ";
            out += brFormat.format(partido.getMenosVotado().getVotos()) + " votos)";

            System.out.println(out);
            i++;
        }
    }

    public void imprimeEleitosPorIdade() {
        int total = this.eleitos.size();

        int idade = 0, menorQue30 = 0, menorQue40 = 0, menorQue50 = 0, menorQue60 = 0, demais = 0;
        for (Candidato c: this.eleitos) {
            idade = c.getIdade(this.diaVotacao);
            if (idade < 30) menorQue30++;
            else if (idade < 40) menorQue40++;
            else if (idade < 50) menorQue50++;
            else if (idade < 60) menorQue60++;
            else demais++;
        }

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        brFormat.setMaximumFractionDigits(2);
        brFormat.setMinimumFractionDigits(2);

        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        System.out.println("    <= Idade < 30: " + menorQue30 + " (" + brFormat.format(100.0 * menorQue30 / total) + "%)");
        System.out.println(" 30 <= Idade < 40: " + menorQue40 + " (" + brFormat.format(100.0 * menorQue40 / total) + "%)");
        System.out.println(" 40 <= Idade < 50: " + menorQue50 + " (" + brFormat.format(100.0 * menorQue50 / total) + "%)");
        System.out.println(" 50 <= Idade < 60: " + menorQue60 + " (" + brFormat.format(100.0 * menorQue60 / total) + "%)");
        System.out.println(" 60 <= Idade     : " + demais + " (" + brFormat.format(100.0 * demais / total) + "%)");
    }

    public void imprimeEleitosPorGenero() {
        int total = this.eleitos.size();
        int mas = 0, fem = 0;
        for (Candidato c: this.eleitos) {
            if (c.getGenero() == 2) mas++;
            else fem++;
        }

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        brFormat.setMaximumFractionDigits(2);
        brFormat.setMinimumFractionDigits(2);

        System.out.println("Eleitos, por gênero:");
        System.out.println("Feminino:  " + fem + " (" + brFormat.format(100.0 * fem / total) + "%)");
        System.out.println("Masculino: " + mas + " (" + brFormat.format(100.0 * mas / total) + "%)");
    }
    
    public void imprimePorcentagensDeVoto() {
        int total = this.votosNominais + this.votosLegenda;

        NumberFormat votos = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        NumberFormat porcentagem = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        porcentagem.setMaximumFractionDigits(2);
        porcentagem.setMinimumFractionDigits(2);

        System.out.println("Total de votos válidos:    " + votos.format(total));
        System.out.println("Total de votos nominais:   " + votos.format(this.votosNominais) + " (" + porcentagem.format(100.0 * this.votosNominais / total) + "%)");
        System.out.println("Total de votos de legenda: " + votos.format(this.votosLegenda) + " (" + porcentagem.format(100.0 * this.votosLegenda / total) + "%)");
    }
}   