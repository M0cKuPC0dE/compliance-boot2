/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author jirawong
 */
@Data
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope = LegalCategory.class)
public class LegalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Department department;
    @ManyToMany
    private Set<UserDetails> owners;
    @ManyToMany
    private Set<UserDetails> approvers;
    @ManyToMany
    private List<LegalDuty> legalDuties;
    @OneToOne
    @JsonIdentityReference(alwaysAsId = false)
    private LegalGroup legalGroup;
    @OneToMany(mappedBy = "legalCategory")
    @JsonManagedReference
    private List<Accord> accords = new ArrayList<>();
}
