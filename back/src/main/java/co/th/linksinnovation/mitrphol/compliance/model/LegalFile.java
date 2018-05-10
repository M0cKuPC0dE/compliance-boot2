/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@Entity
@Data
public class LegalFile implements Serializable{

    @JsonView({JsonViewer.LibraryComplianceView.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    public String name;
    
    @ManyToOne
    @JsonBackReference
    public Compliance compliance;
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    public String uuid;
}
