package nl.amis.custommap.view;

import java.awt.Dimension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class FileHandler {
    
    private UploadedFile file;
    private String imageFileName ="moon_map_big.jpg";
    public static final String imageDirectory ="/home/oracle/images/";


    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public FileHandler() {
        super();
    }
    
    public void handleFileUpload(ActionEvent ae) {
        System.out.println(file.getFilename()+" "+file.getLength());
        try {
            String fileName = imageDirectory + file.getFilename();
            writeToFile(file.getInputStream(), fileName);
System.out.println("Writtewn");
            Dimension imageDimension = getImageDimension(new File(fileName));            
            System.out.println("Dimensions");
            //sendJavascript("setCanvasImage('"+file.getFilename()+"',"+imageDimension.getWidth()+"',"+imageDimension.getHeight()+");");
            String js = "setCanvasImage('"+file.getFilename()+"',"+imageDimension.getWidth()+","+imageDimension.getHeight()+")";
                   sendJavascript(js);
            System.out.println("sent javascript"+js);
                      } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendJavascript(String js) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks =
            Service.getRenderKitService(context, ExtendedRenderKitService.class);
        erks.addScript(context, js);
    }

    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {

        try {
            FileOutputStream out = new FileOutputStream(uploadedFileLocation);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * from StackedOverflow: http://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
     * Gets image dimensions for given file 
     * @param imgFile image file
     * @return dimensions of image
     * @throws IOException if the file is not a known image
     */
    public static Dimension getImageDimension(File imgFile) throws IOException {
      int pos = imgFile.getName().lastIndexOf(".");
      if (pos == -1)
        throw new IOException("No extension for file: " + imgFile.getAbsolutePath());
      String suffix = imgFile.getName().substring(pos + 1);
      Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
      if (iter.hasNext()) {
        ImageReader reader = iter.next();
        try {
          ImageInputStream stream = new FileImageInputStream(imgFile);
          reader.setInput(stream);
          int width = reader.getWidth(reader.getMinIndex());
          int height = reader.getHeight(reader.getMinIndex());
          return new Dimension(width, height);
        } catch (IOException e) {
          System.out.println("Error reading: " + imgFile.getAbsolutePath()+ e);
        } finally {
          reader.dispose();
        }
      }

      throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
    }
}
