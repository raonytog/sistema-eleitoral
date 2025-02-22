public class App {
    public static void main(String[] args) throws Exception {

        SistemaEleitoral se = new SistemaEleitoral(1392, "testes/AC1392/in/candidatos.csv");
        se.contabilizaVotos("testes/AC1392/in/votacao.csv");
    }
}
