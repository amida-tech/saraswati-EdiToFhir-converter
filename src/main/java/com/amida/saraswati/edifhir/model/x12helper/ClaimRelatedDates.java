package com.amida.saraswati.edifhir.model.x12helper;

import lombok.Data;

import java.util.Date;

/**
 * It assembles all the dates in x12/837 claim section.
 *
 * @author warren
 */
@Data

public class ClaimRelatedDates {
    private Date originalCreationDate;
    private Date illnessStartDate;
    private Date treatmentStartDate;
    private Date lastSeenDate;
    private Date accidentDate;
    private Date lastMensPeriodDate;
    private Date lastXRayDate;
    private Date prescriptionDate;
    private Date disabilityDate;
    private Date lastWorkedDate;
    private Date authorizedReturnWorkDate;
    private Date admissionDate;
    private Date dischargeDate;
    private Date assumedCareDate;
    private Date relinguishedCareDate;
    private Date pNc1stContactDate;
    private Date repricedReceivedDate;
}
