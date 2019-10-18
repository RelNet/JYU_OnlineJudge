package RunJudgeProgram;

import CompileFile.CompileMain;
import RunScript.RunLinuxCMD;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class RunJAVA extends Run {
    public RunJAVA(long time, String inID, String inProblemID, boolean hasIn) {
        super(time, inID, inProblemID, hasIn);
    }

    @Override
    public boolean runIt() {
        RunLinuxCMD order;
        String[] commandStrings = {"/bin/sh", "-c",
                "java " + CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "/t" + submitID
        };

        if (hasInput) {     // 如果有输入文件
            File[] inputFileList = getInputFileList();
            Integer count = 1;
            for (File i : inputFileList) {
                if (i.toString().charAt(0) == 't') {
                    continue;
                }

                try {
                    order = new RunLinuxCMD(commandStrings);
                    BufferedReader toInputFile = new BufferedReader(new InputStreamReader(new FileInputStream(i), "utf-8"));
                    BufferedWriter studentInput = new BufferedWriter(new OutputStreamWriter(order.getOutputStream()));
                    String inStr;
                    while ((inStr = toInputFile.readLine()) != null) {
                        studentInput.write(inStr);
                    }
                    order.waitFor(timeout, TimeUnit.MILLISECONDS);

                    order.info();
                    // 此处需要一个处理info的方法，用于检查内存。

                    BufferedReader studentOutput = new BufferedReader(new InputStreamReader(order.getInputStream()));
                    File outputFile = new File(CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "out/" + count.toString() + ".out");
                    count++;
                    outputFile.createNewFile();
                    FileOutputStream toOutputFile = new FileOutputStream(outputFile, true);
                    String str;
                    while ((str = studentOutput.readLine()) != null) {
                        toOutputFile.write(str.getBytes("utf-8"));
                    }
                    // 此处需要一个判断输出文件是否正确的类， 传入Judge类的File是outputFile

                    toInputFile.close();
                    studentInput.close();
                    studentOutput.close();
                    toOutputFile.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                order = new RunLinuxCMD(commandStrings);
                order.waitFor(timeout, TimeUnit.MILLISECONDS);

                order.info();
                // 此处需要一个处理info的方法，用于检查内存。

                BufferedReader studentOutput = new BufferedReader(new InputStreamReader(order.getInputStream()));
                File outputFile = new File(CompileMain.INITIAL_FILE_ADDRESS + "/" + submitID + "out/1.out");
                outputFile.createNewFile();

                FileOutputStream toOutputFile = new FileOutputStream(outputFile, true);
                String str;
                while ((str = studentOutput.readLine()) != null) {
                    toOutputFile.write(str.getBytes("utf-8"));
                }
                // 此处需要一个判断输出文件是否正确的类， 传入Judge类的File是outputFile

                studentOutput.close();
                toOutputFile.close();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
