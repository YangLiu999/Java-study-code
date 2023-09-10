package chatgpt.demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author YL
 * @date 2023/04/06
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class OpenAiApplicationTests {

    @Autowired
    private OpenAiApi openAiApi;

    @Test
    public void createChatCompletion2() throws IOException {
        Scanner in = new Scanner(System.in);
        String input = in.next();
        ChatMessage systemMessage = new ChatMessage("user", input);
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo-0301")
                .messages(messages)
                .user("testing")
                .max_tokens(500)
                .temperature(1.0)
                .build();
        ExecuteRet executeRet = openAiApi.post(PathConstant.COMPLETIONS.CREATE_CHAT_COMPLETION, JSONObject.toJSONString(chatCompletionRequest),
                null);
        JSONObject result = JSONObject.parseObject(executeRet.getRespStr());
        List<ChatCompletionChoice> choices = result.getJSONArray("choices").toJavaList(ChatCompletionChoice.class);
        System.out.println(choices.get(0).getMessage().getContent());
        ChatMessage context = new ChatMessage(choices.get(0).getMessage().getRole(), choices.get(0).getMessage().getContent());
        System.out.println(context.getContent());
    }

}
