package com.jds.model.image;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Color extends Image{
    private String hEX;
}
