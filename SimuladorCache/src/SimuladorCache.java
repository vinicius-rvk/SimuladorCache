

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author viniciusrvk
 */
public class SimuladorCache {
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    	String instrucao;
        int qtdLinha = 0;
        int qtdPalavra = 0;
        int qtdBloco = 0;
        int endereco = 0;
        int valor = 0;
        int numConjuntos = 0;
        int politicaSubstituicao = 0;
        int tipoMapeamento = 0;
        String nomeArquivo = "config.txt";
        File arq = new File(nomeArquivo);
        if(arq.exists()){
            try {

                // carregar arquivo
                FileReader config = new FileReader(arq);
                try( // CARREGAR LEITURA
                        BufferedReader arquivo = new BufferedReader(config)) {
                    try{
                        qtdPalavra = Integer.parseInt(arquivo.readLine());
                        qtdLinha = Integer.parseInt(arquivo.readLine());
                        qtdBloco = Integer.parseInt(arquivo.readLine());
                        tipoMapeamento = Integer.parseInt(arquivo.readLine());
                        numConjuntos = Integer.parseInt(arquivo.readLine());
                        politicaSubstituicao = Integer.parseInt(arquivo.readLine());
                        
                    }catch (IOException erro){
                        System.err.println("config.txt com problemas.");
                    }
                }            
            } catch (IOException | NumberFormatException e) {
                System.err.println("DEU ERRO AO ABRIR O ARQUIVO");
            }
        }else{
            System.err.println("Arquivo não encontrado!");
            instrucao = "sair";
        }
        Mapeamento simulador = new MapDireto(qtdPalavra, qtdLinha, qtdBloco);
        
        switch (tipoMapeamento){
            case 1:
                System.out.println("DIRETO");
                simulador = new MapDireto(qtdPalavra, qtdLinha, qtdBloco);
                break;
            case 2:
                System.out.println("TOTALMENTE ASSOCIATIVO");
                simulador = new MapAssociativo(qtdPalavra, qtdLinha, qtdBloco, politicaSubstituicao);
                break;
            case 3:
                System.out.println("PARCIALMENTE ASSOCIATIVO");
                simulador = new MapParcialmenteAssociativo(qtdPalavra, qtdLinha, qtdBloco, politicaSubstituicao, numConjuntos);
                break;
            default:
                System.out.println("Esse tipo de mapeamento nao existe ou nao foi feito");
        }
        
        Scanner leitor = new Scanner(System.in);
        
        instrucao = "true";
        while (!"sair".equals(instrucao)) {            
            System.out.print("Command> ");
            instrucao = leitor.next();//.toLowerCase();
            switch (instrucao.toLowerCase()) {
                case "read":
                    endereco = leitor.nextInt();
//                    try {
                        simulador.read(endereco);
//                    } catch (Exception e) {
//                        System.out.println("Leitura fora da faixa de endereço da memória!\nTente novamente.");
//                    }
                    //simulador.show();
                    break;
                case "write":
                    endereco = leitor.nextInt();
                    valor = leitor.nextInt();
                    try {
                        simulador.write(endereco, valor);
                    } catch (Exception e) {
                        System.out.println("Escrita fora da faixa de endereço da memória principal!\nTente novamente.");
                    }
                    break;
                case "show":
                    simulador.show();
                    break;
                case "sair":
                    break;
                default:
                    System.out.println("COMANDO ERRADO. TENTE NOVAMENTE");
                    break;
            }
        }leitor.close();
        
    }
    
}
