package cn.addenda.ec.function.handler;

import cn.addenda.ro.error.ROError;
import cn.addenda.ro.error.reporter.ROErrorReporter;

/**
 * @Author ISJINHAO
 * @Date 2021/7/27 22:15
 */
public abstract class ErrorReportableFunctionHandler implements FunctionHandler, ROErrorReporter {

    private final ROErrorReporter errorReporter = new FunctionHandlerROErrorReporterDelegate();

    @Override
    public void error(int errorCode) {
        errorReporter.error(errorCode);
    }

    @Override
    public void error(int errorCode, ROError attachment) {
        errorReporter.error(errorCode, attachment);
    }

}
