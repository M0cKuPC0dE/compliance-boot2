/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.repository;

import co.th.linksinnovation.mitrphol.compliance.model.EvidenceFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jirawong
 */
public interface EvidenceFileRepository extends JpaRepository<EvidenceFile, Long>{
    
}
