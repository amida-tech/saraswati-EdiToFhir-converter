/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component;


import com.amida.saraswati.edifhir.model.edi.component.segment.*;

import java.util.List;

public class Loop2000 {

    private INS memberLevelDetail;
    private REF subscriberIdentifier;
    private REF MemberSupplIdentifier;  // Member supplemental identifier.
    private List<DTP> memberLevelDates;
    private DTP onlineApplicationDate;
    private Loop2100A memberName;
    private Loop2100B incorrectMemberName;
    private Loop2100C memberMailAddress;
    private Loop2100D memberEmployer;
    private Loop2100E memberSchool;
    private Loop2100F custodialPrarent;
    private Loop2100G ResponsiblePerson;
    private Loop2100H dropOffLocation;
    private Loop2200 disabilityInfo;
    private Loop2300 healthCoverage;
    private Loop2500 taxAdvantageAcct;
    private LS additionalRptCategory;
    private Loop2700 memberRptCategory;
    private LE addtionalRptCategoryLoopEnd;
}
