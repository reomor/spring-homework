package task01.service;

public class TestingProcessService {

    private QuestionService questionService;
    private TestingResultCheckService testingResultCheckService;

    public TestingProcessService(QuestionService questionService, TestingResultCheckService testingResultCheckService) {
        this.questionService = questionService;
        this.testingResultCheckService = testingResultCheckService;
    }
}
