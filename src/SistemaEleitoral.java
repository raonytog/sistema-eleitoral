
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class SistemaEleitoral {
    private Map<Integer, Candidato> candidatos = new HashMap<>();
    private Map<Integer, Partido> partidos = new HashMap<>();
    private static int codCargo = 13;
    private final int codMunicipio;
    private List<Candidato> eleitos = new LinkedList<>();
    private List<Candidato> candidatosMaisVotados;
    private List<Partido> partidosMaisVotados;

    public SistemaEleitoral(int codMunicipio, String pathCandidatos) throws Exception {
        InputStream is = new FileInputStream(pathCandidatos);
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        linha = br.readLine();
        this.codMunicipio = codMunicipio;
        
        while (linha != null) {
            Scanner sc = new Scanner(linha).useDelimiter(";");
            int i;

            String nomeCandidato = "", siglaPartido = "";
            int numeroCandidato = 0, codUE = 0, codCargo = 0, genero = 0, 
            eleito = 0, numeroPartido = 0, numeroFederacao = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nascimento = LocalDate.parse("2018-12-27");

            for (i = 0; i < 50; i++) {
                String aux = sc.next();
                
                switch (i) {
                    case 11 -> codUE = Integer.parseInt(aux.substring(1, aux.length() - 1)); //MUDOU DO LAB PRA CASA
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

            if (codUE == codMunicipio && codCargo == SistemaEleitoral.codCargo)
                this.candidatos.put(numeroCandidato, new Candidato(nomeCandidato, numeroCandidato, this.partidos.get(numeroPartido), nascimento, eleito, genero));

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
                    case 11 -> codUE = Integer.parseInt(aux.substring(1, aux.length() - 1)); //MUDOU DO LAB PRA CASA
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
                    if (partido != null) 
                        partido.somaVotosLegenda(qtdVotos);
                }
                else {
                    Candidato candidato = this.candidatos.get(numero);
                    if (candidato != null) 
                        candidato.somaVotos(qtdVotos);
                }
            }

            linha = br.readLine();
            sc.close();
        }
        br.close();

        for (Entry<Integer, Partido> entry : this.partidos.entrySet())
        {
            System.out.println(entry.getValue());
        }
    }
}   
