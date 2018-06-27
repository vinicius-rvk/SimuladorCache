

import java.util.Random;

/**
 *
 * @author viniciusrvk
 */
public class MapAssociativo implements Mapeamento{
    private Cache cache;
    private Memoria memoria;
    private int linhaDisponivel;
    private int []referenciaTempo; //conforme vai sendo acessado, as referencias serão incrementadas
    private int []referenciaFrequencia; //conforme vai sendo acessado, as referencias serão incrementadas
    private int politicaSubstituicao;
    private int tempo;
    /**
     * Cria cache e memoria
     * flush na memoria para iniciar sem endereço já alocado
     * @param qtsPalavra
     * @param qtdLinha
     * @param qtdBloco
     * @param tipo
     */
    public MapAssociativo(int qtsPalavra, int qtdLinha, int qtdBloco, int tipo) {
        cache = new Cache(qtdLinha, qtsPalavra);
        cache.flush();
        memoria = new Memoria(qtdBloco, qtsPalavra);
        this.politicaSubstituicao = tipo;
        iniciaReferencia(qtdLinha); // inicia referencias para politicas de troca;
        linhaDisponivel = -1;// para quando for FIFO ele iniciar no 0
    }
    /**
     * Hit caso o endereço esteja na cache
     * Miss caso o endereço nao seja encontrado na cache; aloca o bloco, que contem o endereço procurado, da memória na cache.
     * Politica de substituição: MAPEAMENTO ASSOCIATIVO
     * Sobrescreve função da interface Mapeamento
     * 
     */
    public void read(int endereco){
        if(!cache.getEndereco(endereco)){
            nextDisponivel(); // veja função
            System.out.println("MISS -> alocado na linha "+(linhaDisponivel)+" o bloco "+memoria.getBloco(endereco).getChave());
            cache.setLinha(linhaDisponivel , memoria.getBloco(endereco));
            referenciaTempo[linhaDisponivel] = ++tempo; 
            referenciaFrequencia[linhaDisponivel] = 1;
        }else{
            System.out.println(" -> HIT -> linha "+(cache.getNuneroLinha(endereco)+1));
            tempo++;
            referenciaFrequencia[cache.getNuneroLinha(endereco)]++;
            referenciaTempo[cache.getNuneroLinha(endereco)] = tempo;
        }
        
    }
    /**
     * Grava no bloco e na memoria o novo valor
     * substitui na cache conforme politica de substituição
     */
    @Override
    public void write(int endereco, int valor){
        Bloco novo = memoria.getBloco(endereco);
        novo.setValor(endereco, valor);
        
//      MUDA NA MEMORIA       
        memoria.setBloco(novo.getChave(), novo);
        if(!cache.getEndereco(endereco)) {
        	cache.setLinha(nextDisponivel(), novo);
            System.out.println("MISS -> alocado na linha "+(linhaDisponivel)+" o bloco "+memoria.getBloco(endereco).getChave());
            referenciaFrequencia[linhaDisponivel]++;
            referenciaTempo[linhaDisponivel] = ++tempo;
            
        }else {
        	cache.setLinha(cache.getNuneroLinha(endereco), novo);
        	referenciaFrequencia[cache.getNuneroLinha(endereco)]++;
        	referenciaTempo[cache.getNuneroLinha(endereco)] = ++tempo;
        }        
    }
    /**
     * Printa no temrinal o estado atual da cache e da memoria.
     */
    @Override
    public void show(){
    	System.out.println("CACHE L1");
        cache.show();
        memoria.show();
    }
    /**
     * A partir do tipo da politica, seta quem será a proxima linha a ser substituida
     * @return linhaDisponivel - proxima linha disponivel
     */
    private int nextDisponivel(){
        switch (politicaSubstituicao){
            case 1:// ALEATORIO
                Random random = new Random();
                linhaDisponivel = random.nextInt(cache.getQtdLinha());
                System.out.println("ALEATORIO");
                break;
            case 2:// FIFO
                System.out.println("FIFO");
                linhaDisponivel = (++linhaDisponivel)%cache.getQtdLinha();
                break;
            case 3:// MENOS FREQUENTEMENTE USADO
                System.out.println("LFU");
                linhaDisponivel = menorDaReferencia(referenciaFrequencia);
                break;
            case 4:// MENOS RECENTEMENTE USADO
                linhaDisponivel = menorDaReferencia(referenciaTempo);
                System.out.println("LRU");
                break;
        }
        return linhaDisponivel;
    }
    /**
     * Inicia referencias para politica de substituição
     * Todos começão com zero.  
     * @param qtdLinha
     */
    private void iniciaReferencia(int qtdLinha){
        referenciaFrequencia = new int[qtdLinha];
        referenciaTempo = new int[qtdLinha];
        for (int i = 0; i < referenciaFrequencia.length; i++) {
            referenciaFrequencia[i] = 0;
            referenciaTempo[i] = 0;
        }
    }
    /**
     * Recebe um array de referencia e retorna o menor valor dentre os indices.
     * @param referencia
     * @return
     */
    private int menorDaReferencia(int []referencia){
        int menor = Integer.MAX_VALUE;
        int indiceMenor = 0;
        for (int i = 0; i < referencia.length; i++) {
            if(referencia[i] < menor){
                menor = referencia[i];
                indiceMenor = i;
            }
                System.out.println(i+" "+referencia[i]);
        }
        return indiceMenor;
    }
}
