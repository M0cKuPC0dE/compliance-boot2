/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.repository;

import co.th.linksinnovation.mitrphol.compliance.model.Compliance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jirawong
 */
public interface ComplianceRepository extends JpaRepository<Compliance, Long>{

    public Optional<Compliance> findByIdAndDeletedIsFalse(Long id);

}
