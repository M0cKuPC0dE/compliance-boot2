/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.Where;

/**
 *
 * @author jirawong
 */
@Data
@Entity
public class Category implements Serializable {

    @JsonView({JsonViewer.LibraryCategoryView.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView({JsonViewer.LibraryCategoryView.class})
    @Column(length = 1000)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Category parent;

    @JsonManagedReference
    @JsonView({JsonViewer.LibraryCategoryView.class})
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Where(clause = "deleted = 0")
    private List<Category> childs;

    private Boolean deleted = false;

    @JsonView({JsonViewer.LibraryCategoryView.class})
    @OneToMany(mappedBy = "category")
    @Where(clause = "deleted = 0")
    private List<Compliance> compliances;

    public void addChild(Category category) {
        if (category == null) {
            return;
        }
        if (this.childs == null) {
            this.childs = new ArrayList<>();
        }
        category.setParent(this);
        this.childs.add(category);
    }
}
