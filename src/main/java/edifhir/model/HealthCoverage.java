package edifhir.model;

public class HealthCoverage {
    
    private String maintenanceTypeCode;
    private String insuranceLineCode;
    private String planCoverageDescription;
    private String coverageLevelCode;
    private Dtp dtp;
    private Ref ref;


    public HealthCoverage(){
        /*empty constructor */
    }


    public HealthCoverage(String maintenanceTypeCode, String insuranceLineCode, String planCoverageDescription, String coverageLevelCode, Dtp dtp, Ref ref) {
        this.maintenanceTypeCode = maintenanceTypeCode;
        this.insuranceLineCode = insuranceLineCode;
        this.planCoverageDescription = planCoverageDescription;
        this.coverageLevelCode = coverageLevelCode;
        this.dtp = dtp;
        this.ref = ref;
    }


    public String getMaintenanceTypeCode() {
        return this.maintenanceTypeCode;
    }

    public void setMaintenanceTypeCode(String maintenanceTypeCode) {
        this.maintenanceTypeCode = maintenanceTypeCode;
    }

    public String getInsuranceLineCode() {
        return this.insuranceLineCode;
    }

    public void setInsuranceLineCode(String insuranceLineCode) {
        this.insuranceLineCode = insuranceLineCode;
    }

    public String getPlanCoverageDescription() {
        return this.planCoverageDescription;
    }

    public void setPlanCoverageDescription(String planCoverageDescription) {
        this.planCoverageDescription = planCoverageDescription;
    }

    public String getCoverageLevelCode() {
        return this.coverageLevelCode;
    }

    public void setCoverageLevelCode(String coverageLevelCode) {
        this.coverageLevelCode = coverageLevelCode;
    }

    public Dtp getDtp() {
        return this.dtp;
    }

    public void setDtp(Dtp dtp) {
        this.dtp = dtp;
    }

    public Ref getRef() {
        return this.ref;
    }

    public void setRef(Ref ref) {
        this.ref = ref;
    }
    
}
