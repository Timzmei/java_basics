import java.io.File;

public class Main
{
    private  static int newWidth = 256;
    public static void main(String[] args)
    {

        Runtime runtime = Runtime.getRuntime();

        int nrOfProcessors = runtime.availableProcessors();

        System.out.println("Number of processors available to the Java Virtual Machine: " + nrOfProcessors);


        String srcFolder = "/home/guliev/Изображения/src";
        String dstFolder = "/home/guliev/Изображения/dst";

        File srcDir = new File(srcFolder);


        File[] files = srcDir.listFiles();

//        startThread(nrOfProcessors, files, dstFolder);


        long start = System.currentTimeMillis();

        int middle = files.length / nrOfProcessors;

        for (int i = 0; i < nrOfProcessors - 1; i++){
            File[] files1 = new File[middle];
            System.arraycopy(files, 0 + (middle * i), files1, 0, middle);
            ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
            new Thread(resizer1).start();
        }

        File[] files2 = new File[files.length - middle * (nrOfProcessors - 1)];

        System.arraycopy(files, middle * (nrOfProcessors - 1), files2, 0, files2.length);
        ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
        new Thread(resizer2).start();


    }


    private static void startThread (int nrOfProcessors, File[] files, String dstFolder){

        long start = System.currentTimeMillis();

        int middle = files.length / nrOfProcessors;

        for (int i = 0; i < nrOfProcessors - 1; i++){
            File[] files1 = new File[middle];
            System.arraycopy(files, 0 + (middle * i), files1, 0, middle);
            ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
            new Thread(resizer1).start();
        }

        File[] files2 = new File[files.length - middle * (nrOfProcessors - 1)];

        System.arraycopy(files, middle * (nrOfProcessors - 1), files2, 0, files2.length);
        ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
        new Thread(resizer2).start();
    }
}
