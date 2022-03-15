package com.jds.model.image;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@Builder
public class ColorPicture implements Comparable<ColorPicture> {

    public static final String PROJECT_IMAGE_DIR_PATH = "src/main/resources/static/";
    public static final Path ROOT_LOCATION;

    static {
        ROOT_LOCATION = Paths.get("");
    }

    private int id;
    private String name;
    private String path;

    public ColorPicture(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int compareTo(ColorPicture colorPicture) {
        return colorPicture.getName().toUpperCase().compareTo(this.getName().toUpperCase());
    }

    public static String pathToFolderPictures(String dir) {
        return ROOT_LOCATION.toString() + PROJECT_IMAGE_DIR_PATH + dir;
    }


}
