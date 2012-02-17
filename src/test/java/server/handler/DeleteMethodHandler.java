package server.handler;

import httpilot.Method;

public class DeleteMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.DELETE;
    }

}
