package com.jds.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "components")
public class MaterialComponents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToMany(mappedBy="parent", fetch = FetchType.EAGER)
    private List<MaterialEntity> materialList;

    @OneToOne(mappedBy = "components")
    private MaterialEntity material;

    public MaterialComponents(MaterialEntity material) {
        this.material = material;

        if (materialList == null) {
            materialList = new ArrayList<>();
        }
        materialList.add(this.material);
    }

    public MaterialComponents(int id) {
        this.id = id;
    }


    public void clearNonSerializingFields(){

    }

    public void setParentToAllMaterials(MaterialComponents components) {
        for(MaterialEntity materials : materialList){
            materials.setParent(components);
        }
    }
}
