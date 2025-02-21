import java.io.*;
import java.io.FileInputStream;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // acho legal os candidatos serem hash map (com o numero de chave) pq vamos ter q entrar varias vezes pra somar os votos, 
        // o mesmo pros partidos pq tbm vamos entrar varias vezes

        /*
         * dei uma olhada e só resumindo oq vai rolar aq:
         * a gnt vai pegar o codigo de municipio e essa eh a primeira linha q importa do csv, o candidato so entra se for do codigo
         * guardados os candidatos e partidos da pra ler o outro csv
         * oq importa no outro eh basicamente o numero de votos, e o numero da coisa q ta votando
         * com hash map vai ficar mto bom
         * acho q o problema eh ter muito mais informação doq a gnt quer, mas oq a gnt precisa fazer não parece taoooo complicado 
        */
        
        /*  
            1. Número de vagas (= número de eleitos); 
            2. Candidatos eleitos (nome completo e na urna), indicado partido e número de votos nominais; 3. Candidatos mais votados dentro do número de vagas; 
            4. Candidatos não eleitos e que seriam eleitos se a votação fosse majoritária; 
            5. Candidatos eleitos no sistema proporcional vigente, e que não seriam eleitos se a votação fosse  majoritária, isto é, pelo número de votos apenas que um candidato recebe diretamente; 
            6. Votos totalizados por partido e número de candidatos eleitos; 
            7. Primeiro e último colocados de cada partido (com nome da urna, número do candidato e total de votos  nominais). Partidos que não possuírem candidatos com um número positivo de votos válidos devem ser ignorados; 
            8. Distribuição de eleitos por faixa etária, considerando a idade do candidato no dia da eleição; 9. Distribuição de eleitos por sexo; 
         */

        /*
            1. Os Relatórios de 2 a 5 devem estar em ordem decrescente de acordo com o número de votos nominais  de cada candidato. Em caso de empate, os candidatos mais velhos terão prioridade. Candidatos que  participam de federações devem ter o nome precedido de “*”. 
            2. O Relatório 6 deve estar em ordem decrescente de acordo com o total de votos do partido (nominais  mais de legenda). Em caso de empate, o com menor número partidário terá prioridade. 
            3. O Relatório 7 deve estar em ordem decrescente de acordo com o total de votos nominais do candidato  mais votado do partido. Em caso de empate, o com menor número partidário terá prioridade. Lembrando  que se dois candidatos tiverem o mesmo número de votos, o mais velho terá prioridade. 
        */
        InputStream is = new FileInputStream("testes/AC1392/in/candidatos.csv");
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        linha = br.readLine();
        
        
        while (linha != null) {
            Scanner sc = new Scanner(linha).useDelimiter(";");
            
            int i;

            String nomeCandidato, siglaPartido;
            int numeroCandidato, codUE, codCargo, genero,
            eleito, numeroPartido, numeroFederacao;

            for (i = 0; i < 50; i++) {
                String aux = sc.next();

                switch (i) {
                    case 11 -> codUE = Integer.parseInt(aux);
                    case 13 -> codCargo = Integer.parseInt(aux);
                    case 16 -> numeroCandidato = Integer.parseInt(aux);
                    case 18 -> nomeCandidato = aux.substring(1, aux.length() - 1);
                    case 25 -> numeroPartido = Integer.parseInt(aux);
                    case 26 -> siglaPartido = aux.substring(1, aux.length() - 1);
                    case 28 -> numeroFederacao = Integer.parseInt(aux);
                    case 36 -> System.out.println("hhdhdajsd");
                    case 38 -> genero = Integer.parseInt(aux);
                    case 48 -> eleito = Integer.parseInt(aux);
                }

                

                if(codCargo == 13 && codUE == 1392)
                {
                    Candidato candidato = new Candidato(nome, numero, partido, data, eleito, genero);
                }
                    
            }
 
            linha = br.readLine();
            sc.close();
        }
        br.close();
    }
}
