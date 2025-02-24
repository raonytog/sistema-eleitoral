public class Main {
    public static void main(String[] args) throws Exception {
        int codMunicipio = Integer.parseInt(args[0]);
        String pathCandidatos = args[1];
        String pathVotacao = args[2];
        String diaVotacao = args[3];

        SistemaEleitoral sistema = new SistemaEleitoral(codMunicipio, pathCandidatos, diaVotacao);
        sistema.contabilizaVotos(pathVotacao);

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

        sistema.imprimeEleitosPorIdade();
        System.out.println();
        
        sistema.imprimeEleitosPorGenero();
        System.out.println();

        sistema.imprimePorcentagensDeVoto();
        System.out.println();
    }
}