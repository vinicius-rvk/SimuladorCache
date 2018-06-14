

/**
 *
 * @author viniciusrvk
 */
public class Cache {
    
    private Bloco[] linhas;
    private int qtdLinha;
    
    Cache(int qtdLinhas, int qtdPalavra){
        linhas = new Bloco[qtdLinhas];
        for(Integer i=0; i<qtdLinhas; i++) {
            linhas[i] = new Bloco(0, 0, qtdPalavra);
        }
        this.qtdLinha = qtdLinhas;
    }
    
    public int getQtdLinha(){return qtdLinha;}
//    Recebe o numero da linha e atualiza o bloco nessa linha[]=bloco
    public void setLinha(int numeroDaLinha, Bloco novo){
        linhas[numeroDaLinha] = novo;
    }
    public void setValor(int endereco, int valor){
        for (int i = 0; i < linhas.length; i++) {
            if(endereco == linhas[i].getEndereco(endereco)){
                linhas[i].setValor(endereco, valor);
            }
        }
    }
//    Recebe o numero da linha e retorna o o bloco da linha[numero da linha]
    public Bloco getLinha(int numeroDaLinha){ return linhas[numeroDaLinha];}
    
    public void flush(){
        for (int i = 0; i < linhas.length; i++) {
            linhas[i].flush();
        }
    }
    public boolean getEndereco(int endereco){
        for (int i=0; i<linhas.length; i++) {
            if(linhas[i].search(endereco)){
                return true;
            }
        }return false;
    }
    public int getNuneroLinha(int endereco){
        for (int i=0; i<linhas.length; i++) {
            if(linhas[i].search(endereco)){
                return i;
            }
        }return -1;
    }
    
    public void show(){
        String a = "\t";
        System.out.println("Line"+a+"Block"+a+"Address"+a+"Value");
        for (int i = 0; i < linhas.length; i++) {
            for (Integer j = 0; j < linhas[1].getQtdPalavra(); j++) {
//                                  linha bloco endereço valor
                System.out.println(i+a+linhas[i].getChave()+a+linhas[i].getEndereco(j)+a+linhas[i].getValor(j));
            }System.out.println("------------------------------");
            
        }
    }
    public void show(int inicio){
        String a = "\t";
        for (int i = 0; i < linhas.length; i++) {
            System.out.println("Line"+a+"Block"+a+"Address"+a+"Value");
            for (Integer j = 0; j < linhas[1].getQtdPalavra(); j++) {
//                                  linha bloco endereço valor
                System.out.println((i+inicio)+a+linhas[i].getChave()+a+linhas[i].getEndereco(j)+a+linhas[i].getValor(j));
            }System.out.println("------------------------------");
            
        }
    }
}
