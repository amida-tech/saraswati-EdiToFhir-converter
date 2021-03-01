/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;
import com.amida.saraswati.edifhir.model.edi.component.x834.segment.DTP834;

import java.util.List;

public class Loop2300 {
    private CLM claimInfo;  // required
    private DTP834 originalCreateDate;  // required
    private DTP834 OnsetOfIllness;
    private DTP834 initialTreatDate;
    private DTP834 lastSeenDate;
    private DTP834 accidentDate;
    private DTP834 LastMenstrualPeriodDate;
    private DTP834 lastXrayDate;
    private DTP834 prescriptionDate;
    private DTP834 DisabilityDates;
    private DTP834 lastWorkedDate;
    private DTP834 authorizedReturnWorkDate;
    private DTP834 admissionDate;
    private DTP834 dischargeDate;
    private DTP834 assumedCareDate;
    private DTP834 relinquishedCareDate;
    private DTP834 pNC1stContactDate;
    private DTP834 repricerReceivedDate;
    private PWK claimSupplInfo;
    private AMT patientAmtPaid;
    private REF predeterminationIdentification;
    private REF mandatoryMedicareXIndicator;
    private REF mammographyCertNumber;
    private REF referalNumber;
    private REF preAuthorization;
    private REF payerClaimControlNo;
    private REF cLIAnumber;
    private REF repricedClaimNumber;
    private REF adjRepricedClaimNumber;
    private REF investigationalDevExemptionNumber;
    private REF claimIdTranInterm;
    private REF medicalRecordNumber;
    private REF demoProjectId;
    private REF carePlanOversight;
    private REF pNCStOfClaimJurisdiction;
    private K3 fileInfo;
    private NTE claimNote;
    private CR1 ambulanceInfo;
    private CR2 spinalManiServiceInfo;
    private CRC homeboundIndicator;
    private CRC ePSDTScreenInfo;
    private HI healthcareDiagnosisCode1to12;
    private HI healthcareDiagnosisCode13to24;
    private HI anesthesialProcedure;
    private HI coditionInfo;
    private HCP claimPricingInfo;

    private Loop2310A referringProvider;
    private Loop2310B renderingProvider;
    private Loop2310C serviceLocationName;
    private Loop2310D supervisingProvider;
    private Loop2310E ambulancePickup;
    private Loop2310F ambulanceDropoff;
    private Loop2320 otherSubscriberInfo;
    private List<Loop2400> serviceLineNumbers;  // up to 50

}
