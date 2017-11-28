/**
 * @author Osmar de Oliveira Braz Junior
 */ 
public class Principal{
	private static final int N = 4;
	private static final int TAMANHOCROMOSSOMO = 4;
	private static final double PERCENTUALMUTACAO = 0.2;
	private static final int NUMEROGERACAO = 10;
	
	int converteBinarioParaDecimal(String v){
		return Integer.parseInt(v, 2);  
	}
	
	String converteDecimalParaBinario(int v){
		return Integer.toString(v, 2);   		
	}
	
	String[] converteVetorDecimalParaBinario(int v[]){
		String binario[] = new String[v.length];
		for(int i = 0; i < v.length; i ++){
			binario[i] = converteDecimalParaBinario(v[i]);
		}
		return binario;
	}
	
	int[] converteVetorBinarioParaDecimal(String v[]){
		int decimal[] = new int[v.length];
		for(int i = 0; i < v.length; i ++){
			decimal[i] = converteBinarioParaDecimal(v[i]);		}
		return decimal;
	}
	
	public void mostrarVetorInteiro(int v[]){
		System.out.println("\nMostra Vetor de Inteiro");
		for(int i = 0; i < v.length; i ++){
			System.out.println("v["+i+"]=" + v[i]);
		}
	}
	
	public void mostrarVetorString(String v[]){
		System.out.println("\nMostra Vetor de String");
		for(int i = 0; i < v.length; i ++){
			System.out.println("v["+i+"]=" + v[i]);
		}
	}
	
	public int somaFuncao(String populacao[]){
		int soma = 0;
		for(int i = 0; i < populacao.length; i ++){
			soma = soma + funcao(populacao[i]);
		}
		return soma;
	}
	
	public void estatisticas(String populacao[]){
		System.out.println("\n>>>Estatisticas");
		System.out.println("Numero\tIndividuo\tx\tf(x)\tProbabilidade\tRoleta");
		double soma = somaFuncao(populacao);
		for(int i = 0; i < populacao.length; i ++){			
			System.out.println(i + "\t" + populacao[i] + "\t\t" + converteBinarioParaDecimal(populacao[i])+"\t"+ funcao(populacao[i]) + "\t" + ((funcao(populacao[i])/soma))*100 + "\t"+ roleta(populacao));			
		}
		//System.out.println("soma:" + soma);
		System.out.println("Soma:\t\t\t\t" + soma);
	}
	
	public int roleta(String populacao[]){
		double soma = somaFuncao(populacao);
		java.util.Random gerador = new java.util.Random();      
		double limite = gerador.nextDouble() * soma;
		double aux = 0;		
		int i=0;
		for(i = 0; ((i < populacao.length) && (aux <limite)); i ++){
			aux = aux + funcao(populacao[i]);
		}
		i--;
		return i;
	}
	
	String[] gerarPopulacaoInicial(){
		String populacao[] = new String[N];
		java.util.Random gerador = new java.util.Random();         
		for(int i = 0; i < N; i++){			
			populacao[i] = "";
			for(int j = 0; j < TAMANHOCROMOSSOMO; j++){			
				boolean resultado = gerador.nextBoolean();
				int valor = (resultado==true?1:0);				
				populacao[i] = populacao[i] + valor;     
			}
		}
		return populacao;
	}	
	
	public int funcao(int x){	
		return (2 * x);
	}
	
	public int funcao(String x){
		return funcao(converteBinarioParaDecimal(x));			
	}
	
	public String crossoverUmPonto(String pai1, String pai2) {		
		String filho="";
		int pontoCorte=(new Double(java.lang.Math.random()*TAMANHOCROMOSSOMO)).intValue();		
		if (java.lang.Math.random()<PERCENTUALMUTACAO) {
			filho=pai1.substring(0,pontoCorte)+pai2.substring(pontoCorte,TAMANHOCROMOSSOMO);
		} else {
			filho=pai2.substring(0,pontoCorte)+pai1.substring(pontoCorte,TAMANHOCROMOSSOMO);
		}				
		return(filho);
	}
	
	public void mutacao(String filho) {
		int i;
		int tamanho = TAMANHOCROMOSSOMO;
		String aux, inicio, fim;		
		for(i=0;i<tamanho;i++) {
			if (java.lang.Math.random()<PERCENTUALMUTACAO) {
				aux=filho.substring(i,i+1);
				if (aux.equals("1")) {
					aux = "0";
				} else {
					aux = "1";
				}
				inicio = filho.substring(0,i);
				fim = filho.substring(i + 1,tamanho);
				filho=inicio + aux + fim;
			}
		}		
	}
	
	public String[] geracao(String populacao[]) {
		String populacaoNova[] = new String[N];		
		String pai1, pai2, filho;
		int i;		
		for(i=0;i<N;++i) {
			pai1 = populacao[roleta(populacao)];
			pai2 = populacao[roleta(populacao)];
			filho = crossoverUmPonto(pai1,pai2);
			mutacao(filho);			
			populacaoNova[i] =filho;
		}
		return populacaoNova;
	}
	
	public void popular(){
		int t = 0;
		String populacao[][] = new String[NUMEROGERACAO][N];
		populacao[t] = gerarPopulacaoInicial();						
		estatisticas(populacao[t]);
		while (t < NUMEROGERACAO-1){
			t = t + 1;
			System.out.println("Calculando nova geracao " + t);
			populacao[t] = geracao(populacao[t-1]);
			estatisticas(populacao[t]);
			
		}
	}
	
	public static void main(String args[]){
		Principal p = new Principal();
		p.popular();		
	}
	
}