package at.fhv.ec.communication.queue;

import at.fhv.ec.application.api.PurchaseService;
import com.google.gson.Gson;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EventListener {
    @Inject
    Logger logger;

    @Inject
    PurchaseService purchaseService;

    @ConfigProperty(name = "redis.queue.name")
    String purchaseEventQueueName;

    private static final Gson GSON = new Gson();

    @ConfigProperty(name = "redis.host")
    String redisHost;

    @ConfigProperty(name = "redis.port")
    int redisPort;

    // TODO: Find out how to start this method because @Startup and @Observes let the application not start
    /*
    void receiveEvents() {
        try(Jedis redisSubscriber = new Jedis(redisHost, redisPort)) {
            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    logger.info("Received event from channel: " + channel);

                    if(channel.equalsIgnoreCase(purchaseEventQueueName)) {
                        DigitalProductPurchasedDTO event = GSON.fromJson(message, DigitalProductPurchasedDTO.class);

                        purchaseService.receivePurchase(event);
                    }
                }
            };

            redisSubscriber.subscribe(jedisPubSub, purchaseEventQueueName);
        }
    }
     */
}
