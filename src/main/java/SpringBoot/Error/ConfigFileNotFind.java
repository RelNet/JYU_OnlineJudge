package SpringBoot.Error;

import java.io.FileNotFoundException;

public class ConfigFileNotFind extends FileNotFoundException {
    String theFile;

    public ConfigFileNotFind(String theFile) {
        this.theFile = theFile;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("未找到配置文件:" + theFile);
    }
}
