/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author jirawong
 */
@Data
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope = LegalGroup.class)
public class LegalGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Location location;
    @ManyToOne
    private BusinessUnit businessUnit;
    private String buName;
    @ManyToMany
    private List<UserDetails> coordinates;
    @ManyToMany
    private List<LegalDuty> legalDuties;
    @Transient
    @JsonProperty
    @JsonIdentityReference(alwaysAsId = false)
    private List<LegalCategory> legalCategories = new ArrayList<>();
}
