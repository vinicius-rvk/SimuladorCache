

import java.util.Random;

/**
 *
 * @author viniciusrvk
 */
public class MapAssociativo implements Mapeamento{
    private Cache cache;
    private Memoria memoria;
    private int linhaDisponivel;
    private int []referenciaTempo;
    private int []referenciaFrequencia;
    private int politicaSubstituicao;
    private int tempo;

    public MapAssociativo(int qtsPalavra, int qtdLinha, int qtdBloco, int tipo) {
        cache = new Cache(qtdLinha, qtsPalavra);
        cache.flush();
        memoria = new Memoria(qtdBloco, qtsPalavra);
        this.politicaSubstituicao = tipo;
        iniciaReferencia(qtdLinha);
        linhaDisponivel = -1;// para quando for FIFO ele iniciar no 0
    }
    
    public void read(int endereco){
        if(!cache.getEndereco(endereco)){
            nextDisponivel();
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
    
    public void show(){
    	System.out.println("CACHE L1");
        cache.show();
        memoria.show();
    }
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
    private void iniciaReferencia(int qtdLinha){
        referenciaFrequencia = new int[qtdLinha];
        referenciaTempo = new int[qtdLinha];
        for (int i = 0; i < referenciaFrequencia.length; i++) {
            referenciaFrequencia[i] = 0;
            referenciaTempo[i] = 0;
        }
    }
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
