import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Random;

public class ExemploParallelStream {

    public static void main(String[] args) {
        List<Long> numeros = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i<10000000;i++){
            numeros.add(random.nextLong());
        }

        LongSummaryStatistics stats = numeros.parallelStream().mapToLong((x)->x).summaryStatistics();
        System.out.println("Maior número na lista: "+ stats.getMax());
        System.out.println("Menor número na lista: "+ stats.getMin());
        System.out.println("Soma de todos os números: "+ stats.getSum());
        System.out.println("Média de todos os números: "+ stats.getAverage());

    }
}
