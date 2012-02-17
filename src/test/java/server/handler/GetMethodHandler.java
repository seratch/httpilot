package server.handler;

import httpilot.Method;

public class GetMethodHandler extends MethodHandler {

    @Override
    public Method getMethod() {
        return Method.GET;
    }

}
