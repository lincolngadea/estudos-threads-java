import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Exemplo retirado do Site DevMedia no link:
 * https://www.devmedia.com.br/threads-paralelizando-tarefas-com-os-diferentes-recursos-do-java
 */
public class ExemploAssincrono1 {

    private static int varCompartilhada = 0;
    private static final Integer QUANTIDADE = 10000;

    /*
      O Exemplo com a lista abaixo gera uma exception, devido a concorrência das threads em adicionar um valor na lista
      para solucionar esse problema, a primeira opção é criarmos uma lista sincronizada
     */
//    private static final List<Integer> VALORES = new ArrayList<>();

    /**
     *
     * A solução abaixo, não resolve o problema completamente, ainda é possível haver problemas
     * de inconsistência com a variável varCompartilhada, devido a concorrência por ela
     * entre as demais threads
     *
     * */

    private static final List<Integer> VALORES = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < QUANTIDADE; i++) {
                    incrementaEAdd();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < QUANTIDADE; i++) {
                    incrementaEAdd();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < QUANTIDADE; i++) {
                    incrementaEAdd();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try{
            t1.join();
            t2.join();
            t3.join();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

        int soma = 0;
        for(Integer valor : VALORES){
            soma += valor;
        }

        System.out.println("Soma: "+soma);

    }

    private synchronized static void incrementaEAdd() {
             VALORES.add(++varCompartilhada);
    }
}
/*
*
* O ato de adquirir bloqueios para sincronizar threads consome tempo,
* mesmo quando nenhuma precisa aguardar a liberação do objeto sincronizado.
* Esse processo é uma faca de dois gumes: se por um lado ele resolve problemas de concorrência,
* por outro serializa o processamento das threads sobre esse bloco; ou seja,
* as threads nunca estarão processando esse código simultaneamente, o que pode degradar o desempenho.
* Portanto, esse recurso deve ser usado com moderação e somente onde for necessário.
*
*/