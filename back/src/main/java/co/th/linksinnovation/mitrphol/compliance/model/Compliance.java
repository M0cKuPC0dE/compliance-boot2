/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.Where;

/**
 *
 * @author jirawong
 */
@Data
@Entity
public class Compliance implements Serializable {

    @JsonView({
        JsonViewer.LibraryCategoryView.class,
        JsonViewer.LibraryComplianceView.class
    })
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView({
        JsonViewer.LibraryCategoryView.class,
        JsonViewer.LibraryComplianceView.class
    })
    @Column(length = 4000)
    private String legalName;
    
    private Integer year;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicDate;
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    @Column(length = 1000)
    private String department;
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    @Column(length = 1000)
    private String ministry;
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    @Lob
    private String important;
    
    @OneToMany(mappedBy = "compliance")
    @Where(clause = "deleted = 0")
    @JsonView(JsonViewer.ComplianceWithLegalDuty.class)
    private List<LegalDuty> legalDuties;

    @JsonView({
        JsonViewer.LibraryCategoryView.class,
        JsonViewer.LibraryComplianceView.class
    })
    @Column(length = 4000)
    private String tags;

    @JsonView({JsonViewer.LibraryComplianceView.class})
    @OneToMany(mappedBy = "compliance")
    @JsonManagedReference
    private List<LegalFile> legalFiles;
    
    @ManyToOne
    @JsonView(JsonViewer.ComplianceWithCategory.class)
    private Category category;
    
    private Boolean deleted = false;
    
    @JsonView({
        JsonViewer.LibraryCategoryView.class,
        JsonViewer.LibraryComplianceView.class
    })
    private Boolean updated = false;
    
    @JsonView({
        JsonViewer.LibraryCategoryView.class,
        JsonViewer.LibraryComplianceView.class
    })
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public List<LegalDuty> addLegalDuties(LegalDuty legalDuty) {
        if (this.legalDuties == null) {
            this.legalDuties = new ArrayList<>();
        }
        this.legalDuties.add(legalDuty);
        return this.legalDuties;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = new Date();
    }
}
