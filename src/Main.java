public class Main {
    public static void main(String[] args) throws Exception {
        int codMunicipio = Integer.parseInt(args[0]);
        String pathCandidatos = args[1];
        String pathVotacao = args[2];
        String diaVotacao = args[3];

        SistemaEleitoral sistema = new SistemaEleitoral(codMunicipio, pathCandidatos, diaVotacao);
        Relatorios relatorios = new Relatorios(sistema);

        sistema.contabilizaVotos(pathVotacao);

        relatorios.imprimeNumeroDeVagas();
        System.out.println();
        
        relatorios.imprimeVereadoresEleitos();
        System.out.println();
        
        relatorios.imprimeCandidatosMaisVotados();
        System.out.println();
        
        relatorios.imprimeSeriamEleitos();
        System.out.println();

        relatorios.imprimeEleitosBeneficiados();
        System.out.println();

        relatorios.imprimePartidosMaisVotados();
        System.out.println();

        relatorios.imprimeExtremosDosPartidos();
        System.out.println();

        relatorios.imprimeEleitosPorIdade();
        System.out.println();
        
        relatorios.imprimeEleitosPorGenero();
        System.out.println();

        relatorios.imprimePorcentagensDeVoto();
        System.out.println();
    }
}