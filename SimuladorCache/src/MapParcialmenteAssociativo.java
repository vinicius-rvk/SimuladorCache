
import java.util.Random;

public class MapParcialmenteAssociativo implements Mapeamento {
	private Cache cache[];
    private Memoria memoria;
    private int linhaDisponivel[];
    private int [][]referenciaTempo;
    private int [][]referenciaFrequencia;
    private int politicaSubstituicao;
    private int tempo;
    private int qtdConjunto;
    private int qtdPalavra;
    private int qtdLinha;


    /**
     * 
     * @param qtdPalavra
     * @param qtdLinha
     * @param qtdBloco
     * @param politicaSubstituicao
     * @param qtdConjunto
     */
	public MapParcialmenteAssociativo(int qtdPalavra, int qtdLinha, int qtdBloco, int politicaSubstituicao, int qtdConjunto) {
		memoria = new Memoria(qtdBloco, qtdPalavra);
		cache = new Cache[(qtdConjunto)];
		linhaDisponivel = new int[(qtdConjunto)];
		for (int i = 0; i < linhaDisponivel.length; i++) { // inicia a linha disponivel dos conjuntos
			if(politicaSubstituicao == 2) {
				linhaDisponivel[i] = -1;
			}else {
				linhaDisponivel[i] = 0;
			}
		}
		for (int i = 0; i < cache.length; i++) { // flush nos conjuntos da cache
			cache[i] = new Cache(qtdLinha/qtdConjunto, qtdPalavra);
			cache[i].flush();
		}
		this.politicaSubstituicao = politicaSubstituicao;
		this.qtdConjunto = qtdConjunto;
		this.qtdLinha = qtdLinha;
		this.qtdPalavra = qtdPalavra;
		iniciaReferencia(qtdLinha/qtdConjunto);
	}
	/**
     * Hit caso o endereço esteja na cache
     * Miss caso o endereço nao seja encontrado na cache; aloca o bloco, que contem o endereço procurado, da memória na cache.
     * Politica de substituição: MAPEAMENTO PARCIALMENTE ASSOCIATIVO
     * Sobrescreve função da interface Mapeamento
     */
	@Override
	public void read(int endereco) {
		if(!cache[n_Conjunto(endereco)].getEndereco(endereco)){
            nextDisponivel(endereco);
            System.out.println("MISS -> alocado na linha "+(linhaDisponivel[n_Conjunto(endereco)]+n_Conjunto(endereco)*qtdLinha)+" o bloco "+memoria.getBloco(endereco).getChave());
            cache[n_Conjunto(endereco)].setLinha(linhaDisponivel[n_Conjunto(endereco)] , memoria.getBloco(endereco));
            referenciaFrequencia[n_Conjunto(endereco)][cache[n_Conjunto(endereco)].getNuneroLinha(endereco)]++;
            referenciaTempo[n_Conjunto(endereco)][cache[n_Conjunto(endereco)].getNuneroLinha(endereco)] = ++tempo;
        }else{
            System.out.println(" -> HIT -> linha "+(cache[n_Conjunto(endereco)].getNuneroLinha(endereco)+1));
        	referenciaFrequencia[n_Conjunto(endereco)][cache[n_Conjunto(endereco)].getNuneroLinha(endereco)]++;
        	referenciaTempo[n_Conjunto(endereco)][cache[n_Conjunto(endereco)].getNuneroLinha(endereco)] = ++tempo;
        }
	}
	/**
     * Grava no bloco e na memoria o novo valor
     * substitui na cache conforme politica de substituição
     */
	@Override
	public void write(int endereco, int valor) {
		Bloco novo = memoria.getBloco(endereco);
        novo.setValor(endereco, valor);
        
        memoria.setBloco(novo.getChave(), novo);// MUDA NA MEMORIA
        if(!cache[n_Conjunto(endereco)].getEndereco(endereco)) {
        	cache[n_Conjunto(endereco)].setLinha(nextDisponivel(endereco), novo);// MUDA NA CACHE
            System.out.println("MISS -> alocado na linha "+(linhaDisponivel[n_Conjunto(endereco)]+n_Conjunto(endereco)*qtdLinha)+" o bloco "+memoria.getBloco(endereco).getChave());
            referenciaFrequencia[n_Conjunto(endereco)][linhaDisponivel[n_Conjunto(endereco)]]++;
            referenciaTempo[n_Conjunto(endereco)][linhaDisponivel[n_Conjunto(endereco)]] = ++tempo;
            
        }else {
        	cache[n_Conjunto(endereco)].setLinha(cache[n_Conjunto(endereco)].getNuneroLinha(endereco), novo);// MUDA NA CACHE
            System.out.println(" -> HIT -> linha "+(cache[n_Conjunto(endereco)].getNuneroLinha(endereco)+1));
        	referenciaFrequencia[n_Conjunto(endereco)][cache[n_Conjunto(endereco)].getNuneroLinha(endereco)]++;
        	referenciaTempo[n_Conjunto(endereco)][cache[n_Conjunto(endereco)].getNuneroLinha(endereco)] = ++tempo;
        }
	}
	/**
     * Printa no temrinal o estado atual da cache e da memoria.
     */
	@Override
	public void show() {
		for (int i = 0; i < cache.length; i++) {
			System.out.println("####\tWay "+i+"\t####");
			cache[i].show(qtdLinha/qtdConjunto*i);
		}
		memoria.show();
	}
	/**
	 * inicia as referencias dos conjuntos
	 * matriz de referencia, pois pra cada conjunto teremos uma referencia diferente
	 * @param qtdLinha
	 */
	private void iniciaReferencia(int qtdLinha){
        referenciaFrequencia = new int[qtdConjunto][];
        referenciaTempo = new int[qtdConjunto][];
        for (int i = 0; i < referenciaFrequencia.length; i++) {
        	referenciaFrequencia[i] = new int[qtdLinha];
        	referenciaTempo[i] = new int[qtdLinha];
            for (int j = 0; j < referenciaFrequencia[i].length; j++) {
				referenciaFrequencia[i][j] = 0;
				referenciaTempo[i][j] = 0;
			}
        }
    }
	/**
     * A partir do tipo da politica, seta quem será a proxima linha a ser substituida
     * Modifica a linhda disponivel do conjunto
     * @return linhaDisponivel - proxima linha disponivel
     */
	private int nextDisponivel(int endereco){
        switch (politicaSubstituicao){
            case 1:// ALEATORIO
                Random random = new Random();
                linhaDisponivel[n_Conjunto(endereco)] = random.nextInt(cache[n_Conjunto(endereco)].getQtdLinha());
                //System.out.println("ALEATORIO");
                break;
            case 2:// FIFO
                //System.out.println("FIFO");
                linhaDisponivel[n_Conjunto(endereco)] = (linhaDisponivel[n_Conjunto(endereco)]++)%cache[n_Conjunto(endereco)].getQtdLinha();
                break;
            case 3:// MENOS FREQUENTEMENTE USADO
                //System.out.println("LFU");
                linhaDisponivel[n_Conjunto(endereco)] = menorDaReferencia(referenciaFrequencia[n_Conjunto(endereco)]);
                break;
            case 4:// MENOS RECENTEMENTE USADO
                linhaDisponivel[n_Conjunto(endereco)] = menorDaReferencia(referenciaTempo[n_Conjunto(endereco)]);
                //System.out.println("LRU");
                break;
        }
        System.out.println(linhaDisponivel[n_Conjunto(endereco)]);
        return linhaDisponivel[n_Conjunto(endereco)];
    }
	/**
	 * Retorna o numero do conjunto q contem o endereço informado
	 * @param endereco
	 * @return
	 */
	private int n_Conjunto(int endereco) {
		return (endereco/qtdPalavra)%qtdConjunto;
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
        }
        return indiceMenor;
    }
}
