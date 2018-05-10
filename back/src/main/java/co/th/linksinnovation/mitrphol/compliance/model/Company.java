/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author jirawong
 */
@Data
@Entity
public class Company {
    @Id
    private String id;
    private String name;
}
