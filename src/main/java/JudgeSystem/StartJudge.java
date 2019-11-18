package JudgeSystem;

import Data.JudgeTask.MainTask;
import Data.Problems.CFProblem;
import Data.Problems.FBProblem;
import Data.Problems.FCFProblem;
import Data.Problems.MCQProblem;
import Data.Submit.MainSubmit;
import Database.OJProblemStatus.ProblemStatus;
import JudgeSystem.CompileFile.CompileGPP;
import JudgeSystem.CompileFile.CompileJAVA;
import JudgeSystem.CompileFile.CompileMain;
import JudgeSystem.run.program.Run;
import JudgeSystem.run.program.RunCPP;
import JudgeSystem.run.program.RunJAVA;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.*;

import static JudgeSystem.run.program.Run.PROBLEM_SET_PATH;

@Getter
@Setter
public class StartJudge {

    public StartJudge() {

    }

    // 任务处理线程池
    private static final Integer MAX_DISTRIBUTE_THREADS = 10;
    private static final Integer MAX_DISTRIBUTE_QUEUE_SIZE = 2000;
    private static final Long DISTRIBUTE_KEEP_ALIVE_TIME = 8L;
    private static final Integer MAX_DISTRIBUTE_CORE_POOL_SIZE = 10;
    ExecutorService distributeThreadPool = new ThreadPoolExecutor(MAX_DISTRIBUTE_CORE_POOL_SIZE, MAX_DISTRIBUTE_THREADS
            , DISTRIBUTE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_DISTRIBUTE_QUEUE_SIZE));

    // 这个是编译线程池的相关设置
    private static final Integer MAX_COMPILE_THREADS = 3;
    private static final Integer MAX_COMPILE_QUEUE_SIZE = 4000;
    private static final Long COMPILE_KEEP_ALIVE_TIME = 2L;
    private static final Integer MAX_COMPILE_CORE_POOL_SIZE = 1;
    ExecutorService compileThreadPool = new ThreadPoolExecutor(MAX_COMPILE_CORE_POOL_SIZE, MAX_COMPILE_THREADS
            , COMPILE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_COMPILE_QUEUE_SIZE));

    // 提交答案线程池，要与数据库进行交互
    private static final Integer MAX_WRITE_THREADS = 100;
    private static final Integer MAX_WRITE_QUEUE_SIZE = 5000;
    private static final Long WRITE_KEEP_ALIVE_TIME = 60L;
    private static final Integer MAX_WRITE_CORE_POOL_SIZE = 30;
    ExecutorService writeAnswerToDatabaseThreadPool = new ThreadPoolExecutor(MAX_WRITE_CORE_POOL_SIZE, MAX_WRITE_THREADS
            , WRITE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_WRITE_QUEUE_SIZE));

    // 编译型任务线程池设置
    private static final Integer MAX_RUN_THREADS = 3;
    private static final Integer MAX_RUN_QUEUE_SIZE = 3000;
    private static final Long RUN_KEEP_ALIVE_TIME = 100L;
    private static final Integer MAX_RUN_CORE_POOL_SIZE = 3;
    ExecutorService runProgramThreadPool = new ThreadPoolExecutor(MAX_RUN_CORE_POOL_SIZE, MAX_RUN_THREADS
            , RUN_KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(MAX_RUN_QUEUE_SIZE));


    void addMission(MainSubmit submit) {
        distributeThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                classifier(submit);
            }
        });
    }

    // 不同问题类型分类器
    void classifier(MainSubmit submit) {
        switch (submit.getProblemType()) {
            case MCQ:
                // 从数据库获取答案集
                List<String> answerList1 = new MCQProblem().GetProblemAnswer(submit.getProblem().getProblemID());
                List<String> userAnswerList = submit.getCourseCodes();
                boolean noteIsRight = true;
                if (answerList1.size() != userAnswerList.size()) {
                    noteIsRight = false;
                } else {
                    // 遍历并比较两个List是不是一样的
                    for (int i = 0; i < answerList1.size() && noteIsRight; i++) {
                        if (!answerList1.get(i).equals(userAnswerList.get(i))) {
                            noteIsRight = false;
                        }
                    }
                }

                // 写入结果到数据库
                ProblemStatus problemStatus1 = new ProblemStatus();
                problemStatus1.setContestID(submit.getContestID());
                problemStatus1.setProblemID(submit.getProblem().getProblemID());
                problemStatus1.setJudgeMode(submit.getJudgeMode());
                problemStatus1.setUser(submit.getUser().getUsername());
                problemStatus1.setContestID(submit.getContestID());
                if (noteIsRight) {
                    problemStatus1.setControlCode(JudgeSystemConstant.AC);
                } else {
                    problemStatus1.setControlCode(JudgeSystemConstant.WA);
                }
                writeAnswerToDatabaseThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        problemStatus1.InsertStatus(problemStatus1);
                    }
                });
                break;
            case FB:
                // 从数据库获取答案集
                List<String> answerList2 = new FBProblem().GetAns(submit.getProblem().getProblemID());
                List<String> userAnswerList2 = submit.getCourseCodes();
                boolean noteIsRight1 = true;
                if (answerList2.size() != userAnswerList2.size()) {
                    noteIsRight1 = false;
                } else {
                    // 遍历并比较两个List是不是一样的
                    for (int i = 0; i < answerList2.size() && noteIsRight1; i++) {
                        if (!answerList2.get(i).equals(userAnswerList2.get(i))) {
                            noteIsRight1 = false;
                        }
                    }
                }

                // 写入结果到数据库
                ProblemStatus problemStatus2 = new ProblemStatus();
                problemStatus2.setContestID(submit.getContestID());
                problemStatus2.setProblemID(submit.getProblem().getProblemID());
                problemStatus2.setJudgeMode(submit.getJudgeMode());
                problemStatus2.setUser(submit.getUser().getUsername());
                problemStatus2.setContestID(submit.getContestID());
                if (noteIsRight1) {
                    problemStatus2.setControlCode(JudgeSystemConstant.AC);
                } else {
                    problemStatus2.setControlCode(JudgeSystemConstant.WA);
                }
                writeAnswerToDatabaseThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        problemStatus2.InsertStatus(problemStatus2);
                    }
                });
                break;
            case FCF:
                StringBuilder tempSourceCode = new StringBuilder(new FCFProblem().GetSampleCode(submit.getProblem().getProblemID()));
                int spaceIndex = tempSourceCode.indexOf("/space");
                // 通过/space转义符替换内容
                for (int i = 0; i < submit.getCourseCodes().size() && spaceIndex != -1; i++) {
                    tempSourceCode.replace(spaceIndex, spaceIndex + 8, " " + submit.getCourseCodes().get(i) + " ");
                    spaceIndex = tempSourceCode.indexOf("/space");
                }
                runProgram(submit, tempSourceCode.toString());
                break;
            case CF:
                runProgram(submit, submit.getCourseCodes().get(0));
                break;
            default:
                throw new NullPointerException("未定义的题目类型");
        }
    }

    // 从源代码文件到运行到写入结果
    void runProgram(MainSubmit submit, String sourceString) {
        String language;
        switch (submit.getLanguageType()) {
            case JAVA:
                language = ".java";
                break;
            case GPP:
                language = ".cpp";
                break;
            default:
                throw new NullPointerException("并不支持的语言");
        }
        ProblemStatus problemStatus3 = new ProblemStatus();
        problemStatus3.setContestID(submit.getContestID());
        problemStatus3.setProblemID(submit.getProblem().getProblemID());
        problemStatus3.setJudgeMode(submit.getJudgeMode());
        problemStatus3.setUser(submit.getUser().getUsername());
        problemStatus3.setContestID(submit.getContestID());
        submit.setSubmitID(problemStatus3.InsertStatus(problemStatus3));

        // 从数据库中获取题目信息
        List<String> problemInfo = new CFProblem().GetCFPMessage(submit.getProblem().getProblemID());

        File sourceFile = new File(CompileMain.INITIAL_FILE_ADDRESS + "/" + submit.getSubmitID().toString()
                + "/" + submit.getSubmitID().toString() + language);

        boolean hasInputFiles;
        File inputDir = new File(PROBLEM_SET_PATH + "/" + submit.getProblem().getProblemID().toString() + "/in");
        if (inputDir.isDirectory()) {
            hasInputFiles = true;
        } else {
            hasInputFiles = false;
        }

        try {
            sourceFile.createNewFile();
            // 写入源代码到文件


            compileThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    CompileMain compiler;
                    switch (submit.getLanguageType()) {
                        case JAVA:
                            compiler = new CompileJAVA(submit.getSubmitID());
                            compiler.compileIt();
                            compiler.setErrorCodes();
                            if (compiler.hasCompileError()) {
                                writeAnswerToDatabaseThreadPool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        problemStatus3.UpdateStatus(submit.getSubmitID(), JudgeSystemConstant.CE);
                                    }
                                });
                                return;
                            } else {
                                runProgramThreadPool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Run runProblem = new RunJAVA(Integer.parseInt(problemInfo.get(3)), submit.getSubmitID().toString()
                                                , submit.getProblem().getProblemID().toString(), hasInputFiles, Integer.parseInt(problemInfo.get(4)));
                                    }
                                });
                            }

                        case GPP:
                            compiler = new CompileGPP(submit.getSubmitID());
                            compiler.compileIt();
                            compiler.setErrorCodes();
                            if (compiler.hasCompileError()) {
                                writeAnswerToDatabaseThreadPool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        problemStatus3.UpdateStatus(submit.getSubmitID(), JudgeSystemConstant.CE);
                                    }
                                });
                            } else {
                                runProgramThreadPool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Run runProblem = new RunCPP(Integer.parseInt(problemInfo.get(3)), submit.getSubmitID().toString()
                                                , submit.getProblem().getProblemID().toString(), hasInputFiles, Integer.parseInt(problemInfo.get(4)));
                                    }
                                });
                            }
                        default:
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


