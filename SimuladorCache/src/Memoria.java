

/**
 *
 * @author viniciusrvk
 */
public class Memoria {
    private Bloco blocos[];
    
    
    public Memoria(int qtdBloco, int qtdPalavra) {
        blocos = new Bloco[qtdBloco];
        for(int i=0; i<qtdBloco; i++) {
            blocos[i] = new Bloco(i, i*qtdPalavra, qtdPalavra);
        }
    }
    
//    recebe o numero do indice e o bloco o qual deve substituir
    public void setBloco(int numeroDoBloco, Bloco novo){
        blocos[numeroDoBloco] = novo;
    }
//    Recebe o numero do endereço e retorna o bloco onde esta o endereço
    public Bloco getBloco(int numeroDoEndereco){
        Bloco tmp = blocos[numeroDoEndereco/blocos[0].getQtdPalavra()];
        return tmp;
    }
    
    public void show(){
        String a = "\t";
        System.out.println("######\tMemory\t######");
        for (Bloco bloco : blocos) {
            System.out.println("Block\tAddress\tValue");
            for (int i = 0; i < bloco.getQtdPalavra(); i++) {
                System.out.println(bloco.getChave()+a+bloco.getEndereco(i)+a+bloco.getValor(i));
            }System.out.println("----------------------");
        }
    }
    
    
    
    
}
