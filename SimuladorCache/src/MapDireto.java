
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
    /**
     * A partir de um endereço, retorna o numero do bloco que contem esse endereço
     * @param endereco
     * @return
     */
    public int blocoToLinha(int endereco){
        return memoria.getBloco(endereco).getChave()%cache.getQtdLinha();
    }
    /**
     * Hit caso o endereço esteja na cache
     * Miss caso o endereço nao seja encontrado na cache; aloca o bloco da memória, que contem o endereço procurado, na cache.
     * Politica de substituição: MAPEAMENTO DIRETO
     * Sobrescreve funções da interface Mapeamento
     * 
     */
    @Override
    public void read(int endereco){
        if(!cache.getEndereco(endereco)){ // SE NAO ENCONTROU NA CACHE, BUSCA NA MEMORIA E ALOCA NA CACHE; PRINTA MISS
            System.out.println("MISS -> alocado na linha "+(this.blocoToLinha(endereco)+1)+" o bloco "+memoria.getBloco(endereco).getChave());
            cache.setLinha(this.blocoToLinha(endereco), memoria.getBloco(endereco));
        }else{ 
            System.out.println(" -> HIT -> linha "+(this.blocoToLinha(endereco)+1));
        }
    }//
    @Override
    public void write(int endereco, int valor){
//        GRAVA NA MEMORIA
        Bloco novo = memoria.getBloco(endereco);// BUSCA BLOCO QUE CONTEM O ENDEREÇO PASSADO E ATRIBUI À novo
        novo.setValor(endereco, valor); // MODIFICA O VALOR DO ENDEREÇO PASSADO
        memoria.setBloco(novo.getChave(), novo); // SOBRESCREVE O BLOCO
        
//       GRAVA NA CACHE ------ FALTA PRINTAR MISS CASO O BLOCO JA NAO ESTIVESSE NA MEMORIA E HIT DO CONTRARIO
        cache.setLinha(blocoToLinha(endereco), novo); // SOBRESCREVE A LINHA COM O NOVO BLOCO
    }
    /**
     * Printa no temrinal o estado atual da cache e da memoria.
     */
    @Override
    public void show(){
        cache.show();
        memoria.show();
    }
    
    
    
}
