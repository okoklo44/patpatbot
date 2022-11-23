package wtf.cockatoo.patpatbot;

import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import wtf.cockatoo.patpatbot.message.MessageVO;
import wtf.cockatoo.patpatbot.service.MessageReceiver;
import wtf.cockatoo.patpatbot.service.MessageSender;

@Named("patpatbot")
public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOG = Logger.getLogger(LambdaHandler.class);

    @Inject
    MessageReceiver receiver;

    @Inject
    MessageSender sender;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, final Context context) {

        try {
            LOG.infof("WebHook Received => %s", new ObjectMapper().writeValueAsString(event));

            final MessageVO messageVO = receiver.getReplyMessage(event.getBody());
            final Response apiResponse = sender.reply(messageVO);

            LOG.infof("Bot Response => %s", apiResponse.readEntity(String.class));
        }
        catch (final Exception e) {
            LOG.errorf(e, "WebHook ERR => %s", e.getMessage());
        }


        /**
         * Proxy integration output format required by AWS.
         * Having "ok" in body is for my convenience while debugging.
         * 
         * https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html
         */
        return new APIGatewayProxyResponseEvent().withBody("ok").withStatusCode(HttpStatus.SC_OK).withIsBase64Encoded(false)
                .withHeaders(Collections.singletonMap("content-type", MediaType.APPLICATION_JSON));
    }

}
