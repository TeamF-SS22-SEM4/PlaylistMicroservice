package at.fhv.ec.application.impl;

import at.fhv.ec.application.PurchaseService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PurchaseServiceImpl implements PurchaseService {

    @Override
    public void receivePurchase() {
        System.out.println("Reveivded purchase");
    }
}
