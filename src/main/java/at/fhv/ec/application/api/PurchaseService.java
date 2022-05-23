package at.fhv.ec.application.api;

import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;

public interface PurchaseService {

    void receivePurchase(DigitalProductPurchasedDTO event);
}
