package com.jds.file;

import com.jds.model.image.ColorPicture;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Service
public class ColorPictureFileRepository {

    private static Logger logger = LoggerFactory.getLogger(ColorPictureFileRepository.class);
    private static final String PATH = "images/index/";
    private static final String TYPE = ".index";

    public void save(String fileName, List<ColorPicture> list) {
        if (!list.isEmpty()) {
            try (FileWriter nFile = new FileWriter(buildLocalName(fileName))) {
                for (ColorPicture picture : list) {
                    nFile.write(picture.getId() + "," + picture.getName() + "," + picture.getPath() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ColorPicture> fine(String fileName) {
        ClassPathResource resource = new ClassPathResource("static/" + PATH + fileName + TYPE);
        List<ColorPicture> result = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            result = bufferedReader.lines()
                    .map(this::createColorPicture)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private ColorPicture createColorPicture(String line) {
        String[] arr = line.split(",");
        ColorPicture result = null;
        if (arr.length == 3) {
            try {
                result = ColorPicture.builder()
                        .id(Integer.parseInt(arr[0]))
                        .name(arr[1])
                        .path(arr[2])
                        .build();
            } catch (Exception e) {
                logger.warn("!ColorPicture failed to create");
            }
        }
        return result;
    }

    private String buildLocalName(String fileName) {
        return ColorPicture.ROOT_LOCATION + ColorPicture.PROJECT_IMAGE_DIR_PATH + PATH +
                fileName + TYPE;
    }
}
