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
import io.quarkus.scheduler.Scheduled;
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

    @Scheduled(every="10s")
    void receiveEvents() {
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
    }
}
