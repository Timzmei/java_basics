import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Loader
{
    public static PrintWriter writer;


    public static void main(String[] args) throws Exception


    {
        ExecutorService service = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

//        FileOutputStream writer = new FileOutputStream("res/numbers.txt");
//        Writer out;
        writer = new PrintWriter("14_Performance/CarNumberGenerator/res/numbers.txt");
        final char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};


        for (int regionCode = 1; regionCode < 100; regionCode++) {
            service.submit(new NumberGenerator(regionCode));
        }
        service.shutdown();

        while (!service.isTerminated()) {
        }


        System.out.println("Время выполнения несколькими потоками - " + (System.currentTimeMillis() - start) + " ms");



        start = System.currentTimeMillis();


        for (int regionCode = 1; regionCode < 100; regionCode++) {

            for (int number = 1; number < 1000; number++) {

                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            StringBuilder builder = new StringBuilder();
                            builder.append(firstLetter);
//                                builder.append(padNumber(number, 3));
                            builder.append(padNumber1(number, 3).toString());
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
//                                builder.append(padNumber(regionCode, 2));
                            builder.append(padNumber1(regionCode, 2).toString());
                            builder.append(" ");
//                                String carNumber = firstLetter + padNumber(number, 3) +
//                                    secondLetter + thirdLetter + padNumber(regionCode, 2);
//                                writer.write(carNumber.getBytes());
//                                writer.write('\n');

                        writer.write(builder.toString());

                        }
                    }
                }
//                writer.write(builder.toString());

            }
        }
        writer.flush();
        writer.close();

        System.out.println("Время выполнения одним потоком - " + (System.currentTimeMillis() - start) + " ms");




    }

    private static String padNumber(int number, int numberLength)
    {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for(int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }

    private static StringBuilder padNumber1(int number, int numberLength)
    {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        StringBuilder padNumbers = new StringBuilder();
        for(int i = 0; i < padSize; i++) {
            padNumbers.append("0");
        }
        padNumbers.append(numberStr);
        return padNumbers;
    }
}
