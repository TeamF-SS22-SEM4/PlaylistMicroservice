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
import org.jboss.logging.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class EventListener {
    @Inject
    Logger logger;

    @Inject
    PurchaseService purchaseService;

    private static final String PURCHASE_EVENT_QUEUE_NAME = "purchasedQueue";

    private static final Gson GSON = new Gson();

    void onStart(/*@Observes TODO uncomment */ StartupEvent startupEvent) {
        //TODO get from env, also currently requires the redis instance to be running
        JedisPool jedisPool = new JedisPool("redis-queue", 6379);

        try (Jedis jedis = jedisPool.getResource()) {
            Thread blockingReceiver = new Thread(() -> {
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

            blockingReceiver.start();
        }
    }
}
