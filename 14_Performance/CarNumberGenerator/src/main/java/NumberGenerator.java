import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.Callable;

public class NumberGenerator implements Runnable {

    public PrintWriter writer;
    public StringBuilder builder;
    public int regionCode;

    final char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};


    public NumberGenerator(int regionCode) throws FileNotFoundException {
        writer = new PrintWriter("14_Performance/CarNumberGenerator/res/numbers" + regionCode + ".txt");
        builder = new StringBuilder();
        this.regionCode = regionCode;

    }

    @Override
    public void run() {
        for (int number = 1; number < 1000; number++) {

            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {

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

//                        writer.write(builder.toString());

                    }
                }
            }
        }
        writer.write(builder.toString());
        writer.flush();
        writer.close();

    }


    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }

    private static StringBuilder padNumber1(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        StringBuilder padNumbers = new StringBuilder();
        for (int i = 0; i < padSize; i++) {
            padNumbers.append("0");
        }
        padNumbers.append(numberStr);
        return padNumbers;
    }

}



