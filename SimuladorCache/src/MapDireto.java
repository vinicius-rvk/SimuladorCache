
/**
 *
 * @author viniciusrvk
 */
// CADA BLOCO SÓ PODE SER ASSOCIADO EM UMA UNICA POSIÇÃO NA CACHE
public class MapDireto implements Mapeamento{
    
    private Cache cache;
    private Memoria memoria;

    public MapDireto(int qtsPalavra, int qtdLinha, int qtdBloco) {
        cache = new Cache(qtdLinha, qtsPalavra);
        cache.flush();
        memoria = new Memoria(qtdBloco, qtsPalavra);
    }
    public int blocoToLinha(int endereco){
        return memoria.getBloco(endereco).getChave()%cache.getQtdLinha();
    }
    public void read(int endereco){
        if(!cache.getEndereco(endereco)){
            System.out.println("MISS -> alocado na linha "+(this.blocoToLinha(endereco)+1)+" o bloco "+memoria.getBloco(endereco).getChave());
            cache.setLinha(this.blocoToLinha(endereco), memoria.getBloco(endereco));
        }else{
            System.out.println(" -> HIT -> linha "+(this.blocoToLinha(endereco)+1));
        }
    }//
    @Override
    public void write(int endereco, int valor){
//        MUDA NA MEMORIA
        Bloco novo = memoria.getBloco(endereco);
        novo.setValor(endereco, valor);
        memoria.setBloco(novo.getChave(), novo);
        
//       MUDA NA CACHE
        cache.setLinha(blocoToLinha(endereco), novo);
    }
    public void show(){
        cache.show();
        memoria.show();
    }
    
    
    
}
