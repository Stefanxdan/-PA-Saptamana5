package com.company;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String args[]) throws IOException, InvalidCatalogException, ClassNotFoundException, URISyntaxException {
        Main app = new Main();
        app.testCreateSave();
        app.testLoadView();
    }

    private void testCreateSave() throws IOException {
        Catalog catalog = new Catalog("Java Resources", "c:/java/catalog.ser");
        Document doc = new Document("java1", "Java Course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        doc.addTag("type", "Slides");
        catalog.add(doc);

        CatalogUtil.save(catalog);
    }

    private void testLoadView() throws InvalidCatalogException, IOException, ClassNotFoundException, URISyntaxException {
        Catalog catalog = CatalogUtil.load("c:/java/catalog.ser");
        Document doc = catalog.findById("java1");
        CatalogUtil.view(doc);
    }
}