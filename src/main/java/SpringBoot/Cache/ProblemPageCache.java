package SpringBoot.Cache;

import Data.Problems.CFProblem;
import Data.Problems.FBProblem;
import Data.Problems.MCQProblem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

public class ProblemPageCache {
    private static final Logger logger = LoggerFactory.getLogger(ProblemPageCache.class);

    /**
     * 获取选择题缓存
     *
     * @return
     */
    @Cacheable(cacheNames = "ProblemPage", cacheManager = "ProblemPageCacheManager")
    public MCQProblem getMCQProblem(Integer problemId) {
        MCQProblem problem = new MCQProblem();
        problem.ProblemOption = problem.GetProblemOption(problemId);
        problem.ProblemAnswer = problem.GetProblemAnswer(problemId);
        problem.ProblemDescribe = problem.GetProblemDescribe(problemId);
        problem.AcceptNumber = problem.GetAcceptNumber(problemId);
        problem.AttemptNumber = problem.GetAttemptNumber(problemId);
        problem.source = problem.GetSource(problemId);
        problem.title = problem.GetTitle(problemId);

        return problem;
    }

    @Cacheable(cacheNames = "ProblemPage", cacheManager = "ProblemPageCacheManager")
    public FBProblem getFBProblem(Integer problemId) {
        FBProblem problem = new FBProblem();
        problem.ProblemDescribe = problem.GetProblemDescribe(problemId);
        problem.AcceptNumber = problem.GetAcceptNumber(problemId);
        problem.AttemptNumber = problem.GetAttemptNumber(problemId);
        problem.source = problem.GetSource(problemId);
        problem.title = problem.GetTitle(problemId);

        return problem;
    }

    @Cacheable(cacheNames = "ProblemPage", cacheManager = "ProblemPageCacheManager")
    public CFProblem getCFProblem(Integer problemId) {
        CFProblem problem = new CFProblem();
        problem.InputDescribe = problem.GetInputDescribe(problemId);
        problem.OutputDescribe = problem.GetOutputDescribe(problemId);
        problem.TimeLimit = problem.GetTimeLimit(problemId);
        problem.MemoryLimit = problem.GetMemoryLimit(problemId);
        problem.SampleInputOutput = problem.GetSampleInputOutput(problemId);
        problem.ProblemDescribe = problem.GetProblemDescribe(problemId);
        problem.AcceptNumber = problem.GetAcceptNumber(problemId);
        problem.AttemptNumber = problem.GetAttemptNumber(problemId);
        problem.source = problem.GetSource(problemId);
        problem.title = problem.GetTitle(problemId);

        return problem;
    }
}
