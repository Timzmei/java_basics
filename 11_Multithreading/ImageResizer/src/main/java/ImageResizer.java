import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {



    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;


    public ImageResizer(File[] files, int newWidth, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }


    @Override
    public void run() {
        try
        {
            for(File file : files)
            {

                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }
                image = ImResizer(image, newWidth * 4, file);
                image = ImResizer(image, newWidth * 2, file);

                image = ImResizer(image, newWidth, file);
                File newFile = new File(dstFolder + "/" + file.getName());

                ImageIO.write(image, "jpg", newFile);

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
//        System.out.println("Finished after start: " + (System.currentTimeMillis() - start) + " ms");


    }

    private BufferedImage ImResizer (BufferedImage image, int newWidth, File file){

        int newHeight = (int) Math.round(
                image.getHeight() / (image.getWidth() / (double) newWidth)
        );
        BufferedImage newImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(image, 0, 0, newWidth, newHeight, null);

        g.dispose();

        return newImage;
    }



}
