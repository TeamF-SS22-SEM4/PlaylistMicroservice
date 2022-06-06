package at.fhv.ec.communication.queue;

import at.fhv.ec.application.api.PurchaseService;
import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;
import com.google.gson.Gson;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EventListener {
    @Inject
    PurchaseService purchaseService;

    private static final Gson GSON = new Gson();

    @ConfigProperty(name = "redis.queue.name")
    String eventQueueName;

    @ConfigProperty(name = "redis.host")
    String redisHost;

    @ConfigProperty(name = "redis.port")
    int redisPort;

    @Scheduled(every = "5s")
    void receiveEvents() {
        JedisPool jedisPool = new JedisPool(redisHost, redisPort);

        try(Jedis jedis = jedisPool.getResource()) {
            List<String> events = jedis.brpop(0, eventQueueName);

            for (String s : events) {
                if(!s.equalsIgnoreCase(eventQueueName)) {
                    DigitalProductPurchasedDTO event = GSON.fromJson(s, DigitalProductPurchasedDTO.class);

                    purchaseService.receivePurchase(event);
                }
            }
        }
    }
}
