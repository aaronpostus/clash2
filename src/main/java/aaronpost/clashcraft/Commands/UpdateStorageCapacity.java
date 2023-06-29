package aaronpost.clashcraft.Commands;

import aaronpost.clashcraft.Arenas.Arena;
import aaronpost.clashcraft.Buildings.Building;
import aaronpost.clashcraft.Currency.Currency;
import aaronpost.clashcraft.Interfaces.IArenaCommand;
import aaronpost.clashcraft.Session;
import aaronpost.clashcraft.Singletons.Sessions;
import java.util.Map;

public class UpdateStorageCapacity implements IArenaCommand {
    public UpdateStorageCapacity() {
        // tell island what its storage capacity should by calculating

    }
    @Override
    public void execute(Arena arena) {
        Session s = Sessions.s.getSession(arena.getPlayer());
        Map<String, Currency> currencyMap = s.getCurrencies();
        for(Currency currency: currencyMap.values()) {
            currency.setMaxAmount(0);
        }
        for (Building building: arena.getIsland().getBuildings()) {
            if(!building.storesCurrency()) {
                continue;
            }
            for(String currency: building.storageCurrencies()) {
                Currency curr = currencyMap.get(currency);
                curr.setMaxAmount(curr.getMaxAmount() + building.getStorageCapacity(currency));
            }
        }
        s.refreshScoreboard();
    }
}
