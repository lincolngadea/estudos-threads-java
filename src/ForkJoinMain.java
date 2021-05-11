import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinMain {

    public static void main(String[] args) {
        ProcessadorDePastas sistema = new ProcessadorDePastas("C:/Windows", ".exe");
        ProcessadorDePastas aplicativos = new ProcessadorDePastas("C:/Program Files", ".exe");
        ProcessadorDePastas documentos = new ProcessadorDePastas("C:/users", ".doc");

        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(sistema);
        pool.execute(aplicativos);
        pool.execute(documentos);

        do {
            System.out.printf("----------------------------------------\n");
            System.out.printf("-> Paralelismo: %d\n", pool.getParallelism());
            System.out.printf("-> Threads Ativas: %d\n", pool.getActiveThreadCount());
            System.out.printf("-> Tarefas: %d\n", pool.getQueuedTaskCount());
            System.out.printf("-> Roubos: %d\n", pool.getStealCount());
            System.out.printf("----------------------------------------\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((!sistema.isDone()) || (!aplicativos.isDone()) || (!documentos.isDone()));

        pool.shutdown();

        List<String> resultados;
        resultados = sistema.join();
        System.out.printf("Sistema: %d aplicativos encontrados.\n", resultados.size());
        resultados = aplicativos.join();
        System.out.printf("Aplicativos: %d encontrados.\n", resultados.size());
        resultados = documentos.join();
        System.out.printf("Documentos: %d  encontrados.\n", resultados.size());
    }
}