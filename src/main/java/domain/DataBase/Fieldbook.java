package domain.DataBase;

import BDDAD.dataAccess.repositories.IrrigationRepository;
import BDDAD.ui.PlotsInSectorUI;
import domain.Irrigation.Fertigation;
import domain.Irrigation.Irrigation;
import domain.Irrigation.IrrigationSystem;
import utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * The type Fieldbook.
 */
public class Fieldbook {

    private static final String fieldbookPath = "docs/Sprint3/LAPR3/USLP11/";
    private static final String fieldbookFile = "fieldbook.csv";
    private static final File fieldbook = new File(fieldbookPath + fieldbookFile);

    /**
     * Write in fieldbook boolean.
     *
     * @param irrigation the irrigation
     * @return the boolean
     */
    public static boolean writeInFieldbook(Irrigation irrigation) {
        try {
            FileWriter fw = new FileWriter(fieldbook, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(irrigation.toString());
            pw.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error while writing irrigation operation " + irrigation.toString() + " in fieldbook file!");
            return false;
        }
    }

    /**
     * Register in fieldbook boolean.
     *
     * @param irrigationRepository the irrigation repository
     * @param irrigationSystem     the irrigation system
     * @param irrigation           the irrigation
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public static boolean registerInFieldbook(IrrigationRepository irrigationRepository, IrrigationSystem irrigationSystem, Irrigation irrigation) throws SQLException {
        Irrigation last = irrigationRepository.getLastIrrigationFromFieldbook();
        if (last == null) {
            Integer recipeId = 0;
            if (irrigation instanceof Fertigation) recipeId = ((Fertigation) irrigation).getMix().getId();
            Integer plotId = Utils.getIds(new PlotsInSectorUI().getPlots(irrigation.getSector().getId())).get(0);
            irrigationRepository.irrigationRegister(irrigation.getId(), plotId, irrigation.getOperationDate(), 6, (float) irrigation.getDurationInMinutes(), irrigation.getSector().getId(), irrigation.getStartTime(), 1, recipeId, (float) 0, 1);
            if (!writeInFieldbook(irrigation)) return false;
            return true;
        }
        for (Irrigation i : irrigationSystem.getIrrigationsSinceLast(last, irrigation)) {
            Integer recipeId = 0;
            if (i instanceof Fertigation) recipeId = ((Fertigation) i).getMix().getId();
            Integer plotId = Utils.getIds(new PlotsInSectorUI().getPlots(irrigation.getSector().getId())).get(0);
            irrigationRepository.irrigationRegister(i.getId(), plotId, i.getOperationDate(), 6, (float) i.getDurationInMinutes(), i.getSector().getId(), i.getStartTime(), 1, recipeId, (float) 0, 1);
            if (!writeInFieldbook(i)) return false;
        }
        return true;
    }

}
