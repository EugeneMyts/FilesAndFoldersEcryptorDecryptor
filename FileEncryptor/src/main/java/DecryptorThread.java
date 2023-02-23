import net.lingala.zip4j.core.ZipFile;

import java.io.File;

public class DecryptorThread extends Thread{

    private GUIForm form;
    private File file;



    private String password;



    public DecryptorThread(GUIForm form) {
        this.form = form;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public void run() {
        onStart();

        try {
        String outPath = getOutPutPath();


            ZipFile zipFile = new ZipFile(file);
            zipFile.setPassword(password);
            zipFile.extractAll(outPath);

        } catch (Exception e) {


            form.showWarning(e.getMessage());
        }
        onFinish();

    }


    private void onStart() {
        form.setButtonsEnabled(false);
    }


    private void onFinish() {

        form.setButtonsEnabled(true);
        form.showFinished();

    }


    private String getOutPutPath() {

        String path = file.getAbsolutePath().replaceAll("\\.enc$", "");
        for(int i = 1; ; i++) {

            String number = i > 1 ? Integer.toString(i) : "";
            String outPath = path + number;
            if (!new File(outPath).exists()) {
                return path;
            }
        }
    }

}
