
/**
 *
 * @author viniciusrvk
 */
public class Bloco {
    private int chave;// bloco
    private int []endereco;
    private int []valor;
    private int qtdPalavra;

    
    Bloco(int chave, Integer enderecoInicio, int qtdPalavra) {
        this.chave = chave;
        this.qtdPalavra = qtdPalavra;
        endereco = new int[qtdPalavra];
        valor = new int [qtdPalavra];
        for(Integer i=0; i<qtdPalavra; i++){
            endereco[i] = enderecoInicio+i;
            valor[i] = 0;
        }
    }
    
    public void flush(){
        for (int i = 0; i < endereco.length; i++) {
            endereco[i] = -1;
        }
        for (int i = 0; i < valor.length; i++) {
            valor[i] = 0;
        }
    }
    
    public boolean search(int endereco){
        for (int i : this.endereco) {
            if(i == endereco){
                return true;
            }
        }
        return false;
    }
    public int getChave(){return chave;}
    
    public int getValor(Integer endereco){return this.valor[endereco];}
    public int getEndereco(int indiceEndereco){return endereco[indiceEndereco%qtdPalavra];}
    public int getQtdPalavra() {
        return qtdPalavra;
    }
    
    public void setValor(int endereco, int valor){
        System.out.println(endereco+" "+valor);
        this.valor[endereco%qtdPalavra] = valor;
    }
}
