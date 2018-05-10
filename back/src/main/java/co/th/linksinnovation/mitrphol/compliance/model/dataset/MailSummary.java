/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model.dataset;

import lombok.Data;

/**
 *
 * @author jirawong
 */
@Data
public class MailSummary {
    private String department;
    private Integer process = 0;
    private Integer accord = 0;
    private Integer noAccord = 0;
    private Integer noProcess = 0;
    private Integer summary = 0;
    
    public MailSummary(){}

    public MailSummary(String department, Integer process, Integer accord, Integer noAccord, Integer noProcess, Integer summary) {
        this.department = department;
        this.process = process;
        this.accord = accord;
        this.noAccord = noAccord;
        this.noProcess = noProcess;
        this.summary = summary;
    }


}
