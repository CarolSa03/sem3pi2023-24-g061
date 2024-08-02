package BDDAD.dataAccess.repositories;

public class Repositories {

    private static final Repositories instance = new Repositories();
    private PlotRepository plotRepository = null;
    private OperationRepository operationRepository = null;
    private ProductionFactorApplianceRepository productionFactorApplianceRepository = null;
    private FertilizationModeRepository fertilizationModeRepository = null;
    private OperationWeedingRepository operationWeedingRepository = null;
    private OperationHarvestingRepository operationHarvestingRepository = null;
    private UnitRepository unitRepository = null;
    private ProductionFactorRepository productionFactorRepository = null;
    private OperationSowingRepository operationSowingRepository = null;
    private CropRepository cropRepository = null;
    private ProductRepository productRepository = null;
    private IrrigationRepository irrigationRepository = null;
    private RecipeRepository recipeRepository = null;
    private IrrigationSystemRepository irrigationSystemRepository = null;
    private SectorRepository sectorRepository = null;

    private Repositories() {
        plotRepository = new PlotRepository();
        operationRepository = new OperationRepository();
        productionFactorApplianceRepository = new ProductionFactorApplianceRepository();
        fertilizationModeRepository = new FertilizationModeRepository();
        operationWeedingRepository = new OperationWeedingRepository();
        unitRepository = new UnitRepository();
        productionFactorRepository = new ProductionFactorRepository();
        operationSowingRepository = new OperationSowingRepository();
        cropRepository = new CropRepository();
        productRepository = new ProductRepository();
        operationHarvestingRepository = new OperationHarvestingRepository();
        irrigationRepository = new IrrigationRepository();
        recipeRepository = new RecipeRepository();
        irrigationSystemRepository = new IrrigationSystemRepository();
        sectorRepository = new SectorRepository();
    }

    public static Repositories getInstance() {
        return instance;
    }

    public PlotRepository getPlotRepository() {
        return plotRepository;
    }

    public void setPlotRepository(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    public OperationRepository getOperationRepository() {
        return operationRepository;
    }

    public void setOperationRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public ProductionFactorApplianceRepository getProductionFactorApplianceRepository() {
        return productionFactorApplianceRepository;
    }

    public void setProductionFactorApplianceRepository(ProductionFactorApplianceRepository productionFactorApplianceRepository) {
        this.productionFactorApplianceRepository = productionFactorApplianceRepository;
    }

    public FertilizationModeRepository getFertilizationModeRepository() {
        return fertilizationModeRepository;
    }

    public void setFertilizationModeRepository(FertilizationModeRepository fertilizationModeRepository) {
        this.fertilizationModeRepository = fertilizationModeRepository;
    }

    public OperationWeedingRepository getOperationWeedingRepository() {
        return operationWeedingRepository;
    }

    public void setOperationWeedingRepository(OperationWeedingRepository operationWeedingRepository) {
        this.operationWeedingRepository = operationWeedingRepository;
    }

    public OperationHarvestingRepository getOperationHarvestingRepository() {
        return operationHarvestingRepository;
    }

    public void setOperationHarvestingRepository(OperationHarvestingRepository operationHarvestingRepository) {
        this.operationHarvestingRepository = operationHarvestingRepository;
    }

    public UnitRepository getUnitRepository() {
        return unitRepository;
    }

    public void setUnitRepository(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public ProductionFactorRepository getProductionFactorRepository() {
        return productionFactorRepository;
    }

    public void setProductionFactorRepository(ProductionFactorRepository productionFactorRepository) {
        this.productionFactorRepository = productionFactorRepository;
    }

    public OperationSowingRepository getOperationSowingRepository() {
        return operationSowingRepository;
    }

    public void setOperationSowingRepository(OperationSowingRepository operationSowingRepository) {
        this.operationSowingRepository = operationSowingRepository;
    }

    public CropRepository getCropRepository() {
        return cropRepository;
    }

    public void setCropRepository(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public IrrigationRepository getIrrigationRepository() {
        return irrigationRepository;
    }

    public void setIrrigationRepository(IrrigationRepository irrigationRepository) {
        this.irrigationRepository = irrigationRepository;
    }

    public RecipeRepository getRecipeRepository() {
        return recipeRepository;
    }

    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public IrrigationSystemRepository getIrrigationSystemRepository() {
        return irrigationSystemRepository;
    }

    public void setIrrigationSystemRepository(IrrigationSystemRepository irrigationSystemRepository) {
        this.irrigationSystemRepository = irrigationSystemRepository;
    }

    public SectorRepository getSectorRepository() {
        return sectorRepository;
    }

    public void setSectorRepository(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

}