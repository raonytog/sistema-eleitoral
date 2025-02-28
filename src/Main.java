import java.io.IOException;
import java.text.ParseException;

/** 
 * Para devido funcionamento, o programa espera receber 4 argumentos da linha de comando:
 * <Codigo do municipio> <caminho do csv de candidatos> <caminho do csv dos votos> <dia da votacao>
 * Caso não siga esses requisitos, o programa se encerrará.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Quantidade de argumentos insuficiente para funcionamento do programa\n");
            return;
        }

        try {
            SistemaEleitoral sistema = new SistemaEleitoral(Integer.parseInt(args[0]), args[1], args[3]);
            sistema.contabilizaVotos(args[2]);

            /** Gera os relatorios */
            Relatorios relatorios = new Relatorios(sistema);

            /** 1 */
            relatorios.imprimeNumeroDeVagas();
            System.out.println();

            /** 2 */
            relatorios.imprimeVereadoresEleitos();
            System.out.println();
            
            /** 3 */
            relatorios.imprimeCandidatosMaisVotados();
            System.out.println();
            
            /** 4 */
            relatorios.imprimeSeriamEleitos();
            System.out.println();

            /** 5 */
            relatorios.imprimeEleitosBeneficiados();
            System.out.println();

            /** 6 */
            relatorios.imprimePartidosMaisVotados();
            System.out.println();

            /** 7 */
            relatorios.imprimeExtremosDosPartidos();
            System.out.println();

            /** 8 */
            relatorios.imprimeEleitosPorIdade();
            System.out.println();

            /** 9 */
            relatorios.imprimeEleitosPorGenero();
            System.out.println();

            /** 10 */
            relatorios.imprimePorcentagensDeVoto();
            System.out.println();

        } catch (IOException e) {
            System.out.println("Erro ao manipular fluxo de entrada e saida.\n");

        } catch (ParseException e) {
            System.out.println("Código do municipio inválido, deve ser passado como um inteiro, sem aspas");

        }
    }
}
