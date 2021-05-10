import java.util.*;

/**
 * Exemplo retirado do Site DevMedia no link:
 * https://www.devmedia.com.br/threads-paralelizando-tarefas-com-os-diferentes-recursos-do-java
 */
public class ExemploAssincrono1 {

    private static int varCompartilhada = 0;
    private static final Integer QUANTIDADE = 10000;

    /**
     * O Exemplo com a lista abaixo gera uma exception, devido a concorrência das threads em adicionar um valor na lista
     * para solucionar esse problema, a primeira opção é criarmos uma lista sincronizada
     */
//    private static final List<Integer> VALORES = new ArrayList<>();

    /**
     *
     * A solução abaixo, não resolve o problema completamente, ainda é possível haver problemas
     * de inconsistência com a variável varCompartilhada, devido a concorrência por ela
     * entre as demais threads
     *
     * */


//    private static final List<Integer> VALORES = Collections.synchronizedList(new ArrayList<>());

    /**
     * Substituição do List por um Set, que suporta a inserção de valores de modo assíncrono.
     */
    private static final Set<Integer> VALORES = new HashSet<>();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < QUANTIDADE; i++){
                    boolean novo = VALORES.add(++varCompartilhada);
                    if(!novo){
                        System.out.println("Já existe: "+varCompartilhada);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean novo = VALORES.add(++varCompartilhada);
                if(!novo){
                    System.out.println("Já existe: "+varCompartilhada);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean novo = VALORES.add(++varCompartilhada);
                if(!novo){
                    System.out.println("Já existe: "+varCompartilhada);
                }
            }
        }).start();
    }
}
/**
 * Ao processar esse código várias vezes vamos perceber que ele vai imprimir valores que já existem
 * em algumas execuções, demonstrando que as threads estão incrementando a variável, mas que em algum
 * momento, geram o mesmo valor. Isso acontece por conta da concorrência pela variável varCompartilhada
 * de maneira assíncrona(antes de um processo finalizar, outro processo começa), onde ao incrementar essa
 * variável, mais de uma thread acaba gerando o mesmo valor.
 */
