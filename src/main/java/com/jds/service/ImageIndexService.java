package com.jds.service;

import com.jds.file.ColorPictureFileRepository;
import com.jds.model.enumModels.TypeOfFurniture;
import com.jds.model.image.ColorPicture;
import com.jds.model.image.TypeImageDirectory;
import com.jds.model.image.TypeOfImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ImageIndexService {

    private static Logger logger = LoggerFactory.getLogger(ImageIndexService.class);

    public static void main(String[] args) {
        ImageIndexService.create();
    }

    public static void create() {
        logger.info("create index...");
        ColorService service = new ColorService();
        Collection<TypeOfFurniture> types = Arrays.asList(TypeOfFurniture.values());

        ColorPictureFileRepository repository = new ColorPictureFileRepository();

        for (TypeOfFurniture type : types) {
            String fileName = TypeImageDirectory.PREVIEW.getPrefix() + type.name();
            List<ColorPicture> list = service.getImageLocalFileList(type.getPicPath());
            repository.save(fileName, list);

            fileName = TypeImageDirectory.SKETCH.getPrefix() + type.name();
            list = service.getImageLocalFileList(type.getSketchPath());
            repository.save(fileName, list);
        }

        Collection<TypeOfImage> typeOfImages = Arrays.asList(TypeOfImage.values());
        for (TypeOfImage type : typeOfImages) {
            String fileName = TypeImageDirectory.PREVIEW.getPrefix() + type.name();
            List<ColorPicture> list = service.getImageLocalFileList(type.getPath());
            repository.save(fileName, list);

            fileName = TypeImageDirectory.MASK.getPrefix() + type.name();
            list = service.getImageLocalFileList(type.getMaskPath());
            repository.save(fileName, list);
        }
        logger.info("...create index");
    }
}
