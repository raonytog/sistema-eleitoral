public class App {
    public static void main(String[] args) throws Exception {
        SistemaEleitoral sistema = new SistemaEleitoral(1392, "testes/AC1392/in/candidatos.csv");
        sistema.contabilizaVotos("testes/AC1392/in/votacao.csv");

        sistema.imprimeNumeroDeVagas();
        System.out.println();
        
        sistema.imprimeEleitos();
        System.out.println();
        
        sistema.imprimeMaisVotados();
        System.out.println();
        
        sistema.imprimeSeriamEleitos();
        System.out.println();

        sistema.imprimeEleitosBeneficiados();
        System.out.println();

        sistema.imprimePartidosMaisVotados();
        System.out.println();

        sistema.imprimeExtremosDosPartidos();
        System.out.println();

        //sistema.imprimeEleitosPorIdade();
        System.out.println();
        
        //sistema.imprimeEleitosPorGenero();
        System.out.println();

        //sistema.imprimeExtremosPartidos();
        System.out.println();

        //sistema.imprimePorcentagensDeVoto();
        System.out.println();
    }
}