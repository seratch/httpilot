package server.handler;

import httpilot.Method;

public class TraceMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.TRACE;
    }

}
