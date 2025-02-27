public class Main {
    public static void main(String[] args) throws Exception {
        int codMunicipio = Integer.parseInt(args[0]);
        String pathCandidatos = args[1];
        String pathVotacao = args[2];
        String diaVotacao = args[3];

        SistemaEleitoral sistema = new SistemaEleitoral(codMunicipio, pathCandidatos, diaVotacao);
        Relatorios relatorios = new Relatorios();

        sistema.contabilizaVotos(pathVotacao);

        relatorios.imprimeNumeroDeVagas(sistema);
        System.out.println();
        
        relatorios.imprimeVereadoresEleitos(sistema);
        System.out.println();
        
        relatorios.imprimeCandidatosMaisVotados(sistema);
        System.out.println();
        
        relatorios.imprimeSeriamEleitos(sistema);
        System.out.println();

        relatorios.imprimeEleitosBeneficiados(sistema);
        System.out.println();

        relatorios.imprimePartidosMaisVotados(sistema);
        System.out.println();

        relatorios.imprimeExtremosDosPartidos(sistema);
        System.out.println();

        relatorios.imprimeEleitosPorIdade(sistema);
        System.out.println();
        
        relatorios.imprimeEleitosPorGenero(sistema);
        System.out.println();

        relatorios.imprimePorcentagensDeVoto(sistema);
        System.out.println();
    }
}