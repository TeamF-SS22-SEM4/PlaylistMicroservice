package at.fhv.ec.communication.queue;

import at.fhv.ec.application.api.PurchaseService;
import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;
import com.google.gson.Gson;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

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

    void receiveEvents(@Observes StartupEvent startupEvent) {
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

        /*
        JedisPool jedisPool = new JedisPool(redisHost, redisPort);

        try (Jedis jedis = jedisPool.getResource()) {
            List<String> events = jedis.brpop(0, PURCHASE_EVENT_QUEUE_NAME);
            logger.info("Received " + events.size() + " events");
            for (String s : events) {
                if(!s.equalsIgnoreCase(PURCHASE_EVENT_QUEUE_NAME)) {

                    DigitalProductPurchasedDTO event = GSON.fromJson(s, DigitalProductPurchasedDTO.class);

                    purchaseService.receivePurchase(event);
                }
            }
        }
        */
    }
}
