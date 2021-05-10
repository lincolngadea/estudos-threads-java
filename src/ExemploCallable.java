import java.util.concurrent.Callable;

public class ExemploCallable implements Callable<String> {

    private final long tempoDeEspera;

    public ExemploCallable(int time) {
        this.tempoDeEspera = time;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(tempoDeEspera);
        return Thread.currentThread().getName();
    }
}
