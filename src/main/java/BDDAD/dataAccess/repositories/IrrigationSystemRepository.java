package BDDAD.dataAccess.repositories;

import domain.Irrigation.IrrigationSystem;

public class IrrigationSystemRepository {

    private IrrigationSystem irrigationSystem;

    public IrrigationSystemRepository() {
        irrigationSystem = new IrrigationSystem();
    }

    public IrrigationSystem getIrrigationSystem() {
        if (irrigationSystem == null) {
            irrigationSystem = new IrrigationSystem();
        }
        return irrigationSystem;
    }

}
