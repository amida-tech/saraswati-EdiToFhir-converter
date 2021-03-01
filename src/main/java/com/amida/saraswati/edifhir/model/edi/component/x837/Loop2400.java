/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;
import com.amida.saraswati.edifhir.model.edi.component.x834.segment.DTP834;

public class Loop2400 {
    private LX serviceLineNumber;
    private SV1 professionalService;
    private TOO toothInfo;
    private SV5 durableMedicalEqpService;
    private PWK lineSupplementalInfo;
    private PWK certOfMedNecessityId;
    private CR1 ambulanceTransportInfo;
    private CR3 durableMedEqpCert;
    private CRC ambulanceCertification;
    private CRC medEqpConditionId;
    private DTP834 serviceDate;
    private DTP834 prescriptionDate;
    private DTP834 recertDate;
    private DTP834 therapyBeginDate;
    private DTP834 lastCertDate;
    private DTP834 lastSeenDate;
    private DTP834 testDate;
    private DTP834 shippedDate;
    private DTP834 lastXrayDate;
    private DTP834 intialTreatDate;
    private QTY ambulancePatientCount;
    private MEA testResult;
    private REF servicePredeterminationIdentification;
    private REF repricedLineItemRefNo;
    private REF adjRepricedLineItemRefNo;
    private REF priorAuthorization;
    private REF lineItemContlNo;
    private REF mammorgraphyCertNo;
    private REF numberOfCLIA;
    private REF referingCLIAId;
    private REF immunizationBatchNo;
    private REF referralNumber;
    private AMT purchasedServiceAmt;
    private AMT postageAmt;
    private AMT customaryCharge;
    private AMT stateTax;
    private K3 fileInfo;
    private NTE lineNote;
    private NTE notes3rdParty;
    private HCP linePricing;
    private CR8 highRiskDevice;

    private Loop2410 drugSupplyId;
    private Loop2420A referingProvider;
    private Loop2420B purchasedService;
    private Loop2420C serviceLocationName;
    private Loop2420D supervisingProvider;
    private Loop2420E orderingProvider;
    private Loop2420F referringProvider;
    private Loop2420G ambulancePickupLocation;
    private Loop2420H ambulanceDropofLocation;
    private Loop2430 lineAdjudicationInfo;
    private Loop2440 formIndentificationCode;

    private SE tsTrailer;
}
