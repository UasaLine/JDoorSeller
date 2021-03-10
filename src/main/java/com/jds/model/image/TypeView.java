package com.jds.model.image;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TypeView {
    private String name;
    private String type;

    public TypeView(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public TypeView(TypeName type){
        name = type.getName();
        this.type = type.toString();
    }

}
