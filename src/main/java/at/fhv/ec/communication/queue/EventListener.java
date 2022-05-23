package at.fhv.ec.communication.queue;

import at.fhv.ec.application.api.PurchaseService;
import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.playlist.PlaylistId;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;
import com.google.gson.Gson;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class EventListener {
    @Inject
    Logger logger;

    @Inject
    PurchaseService purchaseService;

    private static final String PURCHASE_EVENT_QUEUE_NAME = "purchasedQueue";

    private static final Gson GSON = new Gson();

    @ConfigProperty(name = "redis.host")
    String redisHost;

    @ConfigProperty(name = "redis.port")
    int redisPort;

    @Transactional
    void onStart(@Observes StartupEvent startupEvent) {

        JedisPool jedisPool = new JedisPool(redisHost, redisPort);

        try (Jedis jedis = jedisPool.getResource()) {
            List<String> events = jedis.brpop(0, PURCHASE_EVENT_QUEUE_NAME);
            for (String s : events) {
                if(!s.equalsIgnoreCase(PURCHASE_EVENT_QUEUE_NAME)) {
                    logger.debug("received new event " + s);
                    System.out.println(s);

                    DigitalProductPurchasedDTO event = GSON.fromJson(s, DigitalProductPurchasedDTO.class);

                    purchaseService.receivePurchase(event);
                }
            }

            /*
            Thread blockingReceiver = new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                logger.debug("Started queue listener");
                while (true) {
                    List<String> events = jedis.brpop(0, PURCHASE_EVENT_QUEUE_NAME);
                    //first result from brpop is the Key of the list -> discard that
                    for (int i = 1; i < events.size(); i += 1) {
                        logger.debug("received new event");

                        DigitalProductPurchasedDTO event = GSON.fromJson(events.get(i), DigitalProductPurchasedDTO.class);

                        purchaseService.receivePurchase(event);
                    }
                }
            });
             */

            // blockingReceiver.start();
        }
    }
}
