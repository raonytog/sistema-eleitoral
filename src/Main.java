import java.io.IOException;
import java.text.ParseException;

/** 
 * Para devido funcionamento, o programa espera receber 4 argumentos da linha de comando:
 * <Codigo do municipio> <caminho do csv de candidatos> <caminho do csv dos votos> <dia da votacao>
 * Caso não siga esses requisitos, o programa se encerrará.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 4) return;

        try {
            int codMunicipio = Integer.parseInt(args[0]);
            String pathCandidatos = args[1];
            String pathVotacao = args[2];
            String diaVotacao = args[3];

            SistemaEleitoral sistema = new SistemaEleitoral(codMunicipio, pathCandidatos, diaVotacao);
            sistema.contabilizaVotos(pathVotacao);

            /** Gera os relatorios */
            Relatorios relatorios = new Relatorios(sistema);
            relatorios.imprimeNumeroDeVagas();
            System.out.println();

            /** 1 */
            relatorios.imprimeVereadoresEleitos();
            System.out.println();
            
            /** 2 */
            relatorios.imprimeCandidatosMaisVotados();
            System.out.println();
            
            /** 3 */
            relatorios.imprimeSeriamEleitos();
            System.out.println();

            /** 4 */
            relatorios.imprimeEleitosBeneficiados();
            System.out.println();

            /** 5 */
            relatorios.imprimePartidosMaisVotados();
            System.out.println();

            /** 6 */
            relatorios.imprimeExtremosDosPartidos();
            System.out.println();

            /** 7 */
            relatorios.imprimeEleitosPorIdade();
            System.out.println();

            /** 8 */
            relatorios.imprimeEleitosPorGenero();
            System.out.println();

            /** 9 */
            relatorios.imprimePorcentagensDeVoto();
            System.out.println();

        } catch (IOException e) {
            System.out.println("Erro ao manipular fluxo de entrada e saida.\n");

        } catch (ParseException e) {
            System.out.println("Código do municipio inválido, deve ser passado como um inteiro, sem aspas");

        }
    }
}
