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
    private final int   codMunicipio;

    private int qtdEleitos;
    private int votosLegenda;
    private int votosNominais;
    
    private List<Candidato> eleitos = new LinkedList<>();

    /**
     * @param codMunicipio Codigo do municipio em análise
     * @param pathCandidatos Caminho para o arquivo csv com os candidatos
     * @param diaVotacao Dia que aconteceu a apuração dos dados de votacao
     * @throws IOException
     */
    public SistemaEleitoral(int codMunicipio, String pathCandidatos, String diaVotacao) throws IOException {
        InputStream is = new FileInputStream(pathCandidatos);
        InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        linha = br.readLine();

        this.codMunicipio = codMunicipio;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = null;
        this.diaVotacao = LocalDate.parse(diaVotacao, formatter);

        while (linha != null) {
            Scanner sc = new Scanner(linha).useDelimiter(";");
            int i;

            String nomeCandidato = "", siglaPartido = "";
            int numeroCandidato = 0, codUE = 0, codCargo = 0, genero = 0, 
            eleito = 0, numeroPartido = 0, numeroFederacao = 0;

            for (i = 0; i < 50; i++) {
                String aux = sc.next();
                
                /** 
                 * as colunas relevantes e suas informacões 
                 * 11 - codigo do municipio
                 * 13 - codigo do cargo
                 * 16 - numero de votacao do candidato a vereador
                 * 18 - nome do candidato a vereador
                 * 25 - numero do partido pertencente
                 * 26 - sigla do partido pertencente
                 * 28 - numero da federacao pertencente ao partido do candidato
                 * 36 - data de nascimento do candidato
                 * 38 - genero do candidato a vereador
                 * 48 - situacao de eleicao do candidato (se foi eleito por media, por legenda, ...)
                 */
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

            /** Se o partido nao existir, cria e insere-o na hash */
            if (this.partidos.get(numeroPartido) == null) {
                Partido partido = new Partido(numeroPartido, siglaPartido, numeroFederacao);
                this.partidos.put(numeroPartido, partido);  
            }

            /** Caso seja o municipio em analise e vereador e o candidato continua na eleicao */
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

    /**
     * Efetua a leitura e contabilizacao de votos para todos os candidatos e partidos existentes 
     * no sistema
     * @param pathVotacao Caminho para o arquivo csv com as informações de votação
     * @throws IOException
     */
    public void contabilizaVotos(String pathVotacao) throws IOException {
        InputStream is = new FileInputStream(pathVotacao);
        InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        linha = br.readLine();
        
        while (linha != null) {
            Scanner sc = new Scanner(linha).useDelimiter(";");

            int numero = 0, codUE = 0, codCargo = 0, qtdVotos = 0; 
            for (int i = 0; i < 26; i++) {
                String aux = sc.next();
                
                /** 
                 * as colunas relevantes e suas informacões 
                 * 11 - codigo do municipio
                 * 17 - codigo do cargo
                 * 19 - numero de votacao
                 * 21 - quantida de votos 
                 */
                switch (i) {
                    case 11 -> codUE = Integer.parseInt(aux.substring(1, aux.length() - 1));
                    case 17 -> codCargo = Integer.parseInt(aux);
                    case 19 -> numero = Integer.parseInt(aux);
                    case 21 -> qtdVotos = Integer.parseInt(aux);
                }

                if (i == 13 && codUE != this.codMunicipio) break;
                if (i == 17 && codCargo != SistemaEleitoral.codCargo) break;
            }
            
            /**
             * Caso o codigo seja o do municipio que queremos e o cargo seja o 13 (verador)
             * Verificamos se o numero de votacao é de partido ou de candidato e criamos o 
             * respectivo.
             * 
             * Se o numero for de 0 a 99, é um partido
             * Se o numero for de 100 a 999999, é um candidato
             */
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

        /** Ordena a colecao dos candidatos eleitos */
        for (Entry<Integer, Candidato> c : this.candidatos.entrySet())
            this.partidos.get(c.getValue().getNumeroPartido()).addCandidato(c.getValue());
        
        Collections.sort(this.eleitos, new ComparaCandidatos());
    }

    /**
     * @return Lista de partidos ordenados de forma decrescente por votos totais. 
     * Em caso de empate, a ordem fica com o que tiver maior número de votacao do partido
     */
    public List<Partido> ordenaPartidos() {
        List<Partido> lista = new LinkedList<>(this.partidos.values()); 
        Collections.sort(lista, new ComparaPartidos());

        return lista;
    }

    /**
     * @return Lista de partidos ordenados de forma decrescente pelo numero de votos dos candidatos
     * mais votados de cada partido. Em caso de empate, o mais novo ganha
     */
    public List<Partido> ordenaPartidosPorCandidato() {
        List<Partido> lista = new LinkedList<>(this.partidos.values());
        Collections.sort(lista, new ComparaPartidosPorCandidato());

        return lista;
    }

        /**
     * @return Lista de candidatos ordenados de forma decrescente pelo numero 
     * de votos dos candidatos. Em caso de empate, o mais novo ganha
     */
    public List<Candidato> ordenaCandidatos() {
        List<Candidato> lista = new LinkedList<>(this.candidatos.values());
        Collections.sort(lista, new ComparaCandidatos());

        return lista;
    }


    /**
     * @return Retorna a quantidade de vereadores eleitos na eleicao
     */
    public int getQtdEleitos() {
        return this.qtdEleitos;
    }

    /**
     * @return Retorna a lista de candidatos eleitos
     */
    public List<Candidato> getCandidatosEleitos() {
        return new LinkedList<>(this.eleitos);
    }

    
    /**
     * @return Retorna o dia da apuração dos votos
     */
    public LocalDate getDiaVotacao() {
        return this.diaVotacao;
    }

    /**
     * @return Retorna a quantidade de votos de legenda
     */
    public int getVotosLegenda() {
        return this.votosLegenda;
    }

    /**
     * @return Retorna a quantidade de votos nominais
     */
    public int getVotosNominais() { 
        return this.votosNominais;
    }
}   