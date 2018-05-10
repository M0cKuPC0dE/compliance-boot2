/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author jirawong
 */
@Data
@Entity
public class Accord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String remark;
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("id")
    private List<AccordRemark> accordRemark;
    @Enumerated(EnumType.STRING)
    private Accorded accorded;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completeDate;
    @ManyToOne
    @JsonBackReference
    private LegalCategory legalCategory;
    @OneToOne
    private LegalDuty legalDuty;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<LicenseFile> licenseFiles;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<EvidenceFile> evidenceFiles;
    private Boolean accept;
    private Boolean approve;
    
    public void addRemark(AccordRemark accordRemark){
//        if(accordRemark.getRemark() == null || accordRemark.getRemark().equals("")){
//            return;
//        }
        if(this.accordRemark == null){
            this.accordRemark = new ArrayList<>();
        }
        this.accordRemark.add(accordRemark);
    }
}
