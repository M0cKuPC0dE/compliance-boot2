/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

/**
 *
 * @author jirawong
 */
public class JsonViewer {
    public interface CategoryWithCompliance extends ComplianceWithLegalDuty {}
    public interface ComplianceWithCategory extends ComplianceWithLegalDuty{}
    
    public interface LegalDutyWithCompliance{}
    public interface ComplianceWithLegalDuty{}
    
    public interface LibraryCategoryView{}
    public interface LibraryComplianceView{}
}
