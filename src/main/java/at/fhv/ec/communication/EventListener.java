package at.fhv.ec.communication;

import at.fhv.ec.application.PurchaseService;
import io.quarkus.runtime.StartupEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EventListener {

    private static final String PURCHASE_EVENT_QUEUE_NAME = "purchasedQueue";

    @Inject
    private PurchaseService purchaseService;


    void onStart(@Observes StartupEvent startupEvent) {
        try {
            //TODO get from env, also currently requires the redis instance to be running
            JedisPool jedisPool = new JedisPool("localhost", 6379);

            try (Jedis jedis = jedisPool.getResource()) {
                Thread blockingReceiver = new Thread(() -> {
                    while (true) {
                        List<String> events = jedis.brpop(0, PURCHASE_EVENT_QUEUE_NAME);
                        //first result from brpop is the Key of the list -> discard that
                        for (int i = 1; i < events.size(); i += 1) {
                            System.out.println("received"  + events.get(i));
                            //TODO parse from json (move event to shared lib then use gson) and send to application service
                        }
                    }
                });
                blockingReceiver.start();
            }
        } catch (Exception e) {
            //TODO remove this try/catch, once redis is also in kluster
        }
    }
}
