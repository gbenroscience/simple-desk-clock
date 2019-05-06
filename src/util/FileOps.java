/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gbemiro Jiboye
 */
public class FileOps {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private static File saveDir;
    private static File saveFile;

    private static final String FILE_DIR = "G-Clock";
    private static final String FILE_NAME = "data";

    static {
        saveDir = new File(System.getProperty("user.home"), FILE_DIR);
        saveFile = new File(saveDir, FILE_NAME);
        if (!saveDir.exists() || !saveDir.isDirectory()) {
            saveDir.mkdir();
        }

        if (!saveFile.exists() || !saveFile.isFile()) {
            try {
                saveFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * @param fileName The path to the file to write to.
     * @return the object stored in the file.
     */
    public String read(String fileName) {
        String str = "";
        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
        } catch (IOException ion) {
        }

        try {
            Clock clock = (Clock) in.readObject();
        } catch (ClassNotFoundException classErr) {
        } catch (IOException ion) {
        }
        return str;
    }

    /**
     *
     * @param fileName The path to the file to write to.
     * @param obj The object to save.
     */
    public void write(String fileName, Object obj) {
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
        } catch (IOException ion) {
        }

        try {
            out.writeObject(obj);
        } catch (IOException ion) {
        }
    }

    /**
     *
     * @return the object stored in the file.
     */
    public Clock read() {

        try {
            in = new ObjectInputStream(new FileInputStream(saveFile));
            Clock clock = (Clock) in.readObject();
            if(clock != null){
                clock.show();
            }
            return clock;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    /**
     *
     * @param clock The clock to save
     *
     */
    public void write(Clock clock) {
        try {
            out = new ObjectOutputStream(new FileOutputStream(saveFile));
        } catch (IOException ion) {
        }

        try {
            out.writeObject(clock);
        } catch (IOException ion) {
        }

        close();
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }

        } catch (IOException ionic) {

        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

}
