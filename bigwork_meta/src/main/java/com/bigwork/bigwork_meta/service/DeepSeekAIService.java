package com.bigwork.bigwork_meta.service;

import java.io.IOException;

public interface DeepSeekAIService {
    String ask(String question,String workspaceId) throws IOException, InterruptedException;
}
